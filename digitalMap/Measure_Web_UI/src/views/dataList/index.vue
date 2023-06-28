<template>
  <div class="app-container">
    <div class="form_container">
      <div class="left_form">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="100px">
          <el-form-item :key="prop.fieldProp" :prop="prop.fieldProp"
                        v-for="(prop, index) in headerList"
                        v-show="showSearch(index)">
            <span slot="label">
                <span> {{ prop.fieldName.length > 6 ? prop.fieldName.substr(0, 6) : prop.fieldName }}
                <el-tooltip v-if="prop.fieldName.length>6" :content="prop.fieldName">
                  <i class="el-icon-question"></i> </el-tooltip>
                </span>
            </span>

            <el-select style="flex-basis: 65%" filterable multiple
                       v-if="prop.fieldProp==='model' || prop.fieldProp==='planeNo' "
                       clearable
                       collapse-tags
                       :filter-method="(query)=>filterCheckPerOptions(query,prop.fieldProp,prop.fieldProp)"
                       v-model="queryParams.parameters[prop.fieldProp]" placeholder="全部">
              <el-option
                v-for="item in selectOptions[prop.fieldProp].top100"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
              <el-option
                v-if="selectOptions[prop.fieldProp].top100.length >= 100 && selectOptions[prop.fieldProp].list.length > 100"
                value="tip" disabled>
                <span class="gray">--只展示前100条数据，更多数据请输入查询--</span>
              </el-option>
            </el-select>
            <el-select style="flex-basis: 65%" filterable multiple
                       v-else-if="prop.fieldPropType==='select' "
                       clearable
                       collapse-tags
                       v-model="queryParams.parameters[prop.fieldProp]" placeholder="全部">
              <el-option
                v-for="item in prop.fieldOptions?prop.fieldOptions.split(','):[]"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
            <el-input v-else v-model="queryParams.parameters[prop.fieldProp]" placeholder="请输入" clearable/>
          </el-form-item>

          <el-form-item>
            <div @click="searchOpen = !searchOpen" style="color: #409eff;margin-left: 10px">
              <i :class="searchOpen ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
              <span style="margin-left: 8px">{{ searchOpen ? '折叠条件' : '展开条件' }}</span>
            </div>
          </el-form-item>

        </el-form>
      </div>
      <div class="right_btn">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </div>

    </div>
    <div class="list_container">
      <el-row :gutter="10" class="mb8 head_btn">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-upload" size="mini" @click="handleImport"
                     v-has-permi="['data:upload']">导入数据
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-download" size="mini" @click="handleExportData"
                     v-has-permi="['data:download']">导出数据
          </el-button>
        </el-col>
        <div style="float: right">
          <el-popover
            placement="right"
            width="400"
            trigger="click">
            <el-table :data="columnSetting" height="500">
              <el-table-column property="fieldName" label="列名"></el-table-column>
              <el-table-column property="fixed" label="固定列">
                <template slot-scope="scope">
                  <el-switch
                    v-model="scope.row.fixed"
                    :active-value="true"
                    :inactive-value="false">
                  </el-switch>
                </template>
              </el-table-column>
              <el-table-column property="show" label="显示列">
                <template slot-scope="scope">
                  <el-switch
                    v-model="scope.row.show"
                    :active-value="true"
                    :inactive-value="false">
                  </el-switch>
                </template>
              </el-table-column>
            </el-table>
            <el-button type="text" slot="reference" circle icon="el-icon-setting">列设置</el-button>
          </el-popover>
        </div>
      </el-row>

      <el-table v-loading="loading" :data="list" stripe border>
        <el-table-column type="index" label="序号" fixed></el-table-column>
        <el-table-column
          header-align="center" align="center"
          :render-header="renderHeader"

          v-for="(prop,index) in headerList" :key="prop.fieldProp+index"
          :label="prop.fieldName"
          :fixed="columnSetting.findIndex(v => v.fixed===true && v.fieldProp===prop.fieldProp)>=0"
          v-if="columnSetting.findIndex(v => v.show===true && v.fieldProp===prop.fieldProp)>=0"
          :prop="prop.fieldProp">
          <template slot-scope="scope">
            <el-tooltip popper-class='table-tooltip' :content="scope.row[prop.fieldProp]" placement="top">
              <div class="long_title">
                <span>{{ scope.row[prop.fieldProp] }}</span>
              </div>
            </el-tooltip>

          </template>
        </el-table-column>

        <el-table-column label="操作" width="220px" fixed="right">
          <template slot-scope="scope">
            <!--            <el-button type="text" v-if="scope.row.source==='system'"
                                   @click="handleDelete(scope.row)">删除
                        </el-button>-->
            <el-button type="text" v-has-permi="['data:edit']" @click="handleUpdate(scope.row)">修改
            </el-button>
            <el-button type="text" v-has-permi="['data:workInfo']"
                       v-show="scope.row['workflowId']"
                       @click="handleViewWorkInfo(scope.row)">查看工序
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                  @pagination="getList"/>
    </div>

    <el-dialog :title="update.title" :visible.sync="update.open" width="40%" append-to-body
               :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :key="prop.fieldProp" :prop="prop.fieldProp"
                      v-for="(prop, index) in headerList">
          <span slot="label">
            <span> {{ prop.fieldName.length > 6 ? prop.fieldName.substr(0, 6) : prop.fieldName }}
            <el-tooltip v-if="prop.fieldName.length>6" :content="prop.fieldName">
              <i class="el-icon-question"></i> </el-tooltip>
            </span>
        </span>
          <el-input v-if="form.source === 'system' ? true : !(prop.fieldType === 1)" v-model="form[prop.fieldProp]"
                    placeholder="请输入"/>
          <div v-else style="  white-space: pre-wrap;">
            <span class="">{{ form[prop.fieldProp] }}</span>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                 :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
                 :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <!--          <div class="el-upload__tip" slot="tip">
                      <el-checkbox v-model="upload.updateSupport"/>
                      是否更新已经存在的用户数据
                    </div>-->
          <span>仅允许导入xls、xlsx格式文件。</span>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="workInfo.title" :visible.sync="workInfo.open" width="40%" append-to-body
               :close-on-click-modal="false">
      <div v-html="workInfo.content"></div>
      <!--      <editor type="html" :read-only="true" :value="workInfo.content"></editor>-->
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listConfig} from "@/api/biz/fieldConfig";
import {listData, updateData, getWorkInfo,downloadData} from "@/api/biz/dataList";
import {getToken} from "@/utils/auth";
import {statisticAllequipment, statisticAllModel, statisticAllPlaneNo} from "@/api/biz/statistic";

export default {
  name: 'index',
  data() {
    return {
      loading: true,
      searchOpen: false,
      list: [],
      headerList: [],
      queryParams: {
        parameters: {},
        pageNum: 0,
        pageSize: 10,
      },
      // 总条数
      total: 0,
      form: {},
      rules: {},
      update: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
      },
      workInfo: {
        // 是否显示弹出层（查看工序）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        content: "",
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/data/upload"
      },
      columnSetting: [],
      selectOptions: {
        equipmentName: {
          list: [],
          top100: []
        }, model: {
          list: [],
          top100: []
        }, planeNo: {
          list: [],
          top100: []
        },
      }
    }
  },
  created() {
    listConfig().then(response => {
      this.headerList = response.data;
      this.columnSetting = this.headerList.map((item, index) => {
        return {
          fieldName: item.fieldName,
          fieldProp: item.fieldProp,
          fixed: false,
          show: true,

        }
      })
    });
    statisticAllequipment().then(res => {
      let list = res.data
      let top100 = list.slice(0, 100)
      this.selectOptions['equipmentName'] = {
        list: list,
        top100: top100
      }


    })
    statisticAllModel().then(res => {
      let list = res.data
      let top100 = list.slice(0, 100)
      this.selectOptions['model'] = {
        list: list,
        top100: top100
      }

    })
    statisticAllPlaneNo().then(res => {
      let list = res.data
      let top100 = list.slice(0, 100)
      this.selectOptions['planeNo'] = {
        list: list,
        top100: top100
      }

    })

    this.getList()
  },
  methods: {
    getList() {
      this.loading = true;

      listData(this.queryParams).then(response => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      })

    },
    renderHeader(h, {column, $index}) {
      console.log(column.label)
      /*      let numble = column.lable ? column.lable.length : 12 // 表头字数
            let size = 16 // 字体尺寸
            column.minWidth = numble * size + 20 // 计算宽度 20=padding
           // return h('div', [column.lable], {class: 'table-head', style: {width: '100%'}},)
            return h('div', column.label);*/
      let realWidth = 0;
      let span = document.createElement('span');

      span.innerText = column.label;
      document.body.appendChild(span);

      realWidth = span.getBoundingClientRect().width;
      column.minWidth = realWidth < 50 ? 80 : realWidth + 30; // 可能还有边距/边框等值，需要根据实际情况加上

      document.body.removeChild(span);
      return h('span', column.label);
    },

    showSearch(index) {
      if (this.searchOpen) {
        return true;
      } else {
        return index < 3
      }
    },
    filterCheckPerOptions(query = '', optionKey, queryKey) {

      let maxLength = 100
      // query是输入框中的检索条件
      var arr = this.selectOptions[optionKey].list.filter(item => {
        return item && item.includes(query)
      })
      // 根据检索条件筛选出来的选项，只取前100条
      if (arr.length > maxLength) {
        arr = arr.slice(0, maxLength)
      }
      // 清空之前的选项
      this.selectOptions[optionKey].top100.splice(0, this.selectOptions[optionKey].top100.length)
      // chosen表示已被选择的选项，添加这一部分主要是为了回显，避免选择框中直接出现用户id
      const chosen = this.queryParams.parameters[queryKey] ? this.queryParams.parameters[queryKey] : []
      // 检索项 + 已选项的并集
      const result = [...chosen.filter(item => !arr.includes(item)), ...arr]
      if (arr.length > maxLength) {
        this.selectOptions[optionKey].top100.push(...result)
      } else {
        this.selectOptions[optionKey].top100.push(...result)

      }
    },
    /** 导出模板按钮操作 */
    handleExportData() {
      // downloadData('/system/data/download', this.queryParams.parameters, `导出数据_${new Date().getTime()}.xlsx`)
      this.downloadPostJsonBody('/system/data/download', this.queryParams.parameters, `导出数据_${new Date().getTime()}.xlsx`)
    },
    handleQuery() {
      console.log(this.queryParams)
      this.getList()

    },
    resetQuery() {
      this.queryParams = {
        parameters: {},
        pageNum: 0,
        pageSize: 10,
      }
      this.handleQuery()
    },
    handleDelete(row) {
      if (row.source === 'system') {
        this.$modal.confirm("您确认要删除此项数据吗").then(value => {

        }).catch(reason => {
        })

      } else {
        this.$message.warning("不可删除！")
      }
    },
    handleUpdate(row) {
      this.update.title = '修改数据'
      this.update.open = true
      this.form = row
      console.log()
    },
    handleViewWorkInfo(row) {
      getWorkInfo({
        id: row.workflowId,
        source: row['source']
      }).then(res => {
        this.workInfo.title = '查看工序'
        this.workInfo.open = true
        this.workInfo.content = res.data
      })


    },
    submitForm() {
      this.$modal.loading("正在提交");

      updateData(this.form).then(value => {
        this.$message.success("修改成功！")
        this.update.open = false
        this.$modal.closeLoading()
      })
    },
    cancel() {
      this.workInfo.open = false
      this.update.open = false
    },
    handleImport() {
      this.upload.title = "数据导入";
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
}
</script>

<style lang="scss" scoped>
.long_title {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.left_form::v-deep .el-form-item {
  display: inline-block;
}

</style>
