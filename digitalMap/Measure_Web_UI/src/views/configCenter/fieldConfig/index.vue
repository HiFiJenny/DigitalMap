<template>
  <div class="app-container">
    <div class="form_container">
      <div class="left_form">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
          <el-form-item label="字段名称" prop="fieldName">
            <el-input v-model="queryParams.fieldName" placeholder="请输入字段名称" clearable
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <!--          <el-form-item label="字段属性" prop="fieldPropType">
                      <el-select v-model="queryParams.fieldPropType" placeholder="请选择" clearable>
                        <el-option v-for="item in dict.type['field_prop_type']" :key="item.value" :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>-->
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
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                     v-hasPermi="['field:config:add']">新建
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-download" size="mini" @click="handleExport"
                     v-hasPermi="['field:config:download']">导出模板
          </el-button>
        </el-col>

      </el-row>

      <el-table v-loading="loading" :data="headerList" stripe border>
        <el-table-column type="index" label="序号"></el-table-column>
        <el-table-column label="字段名称" align="center" prop="fieldName"/>
        <el-table-column label="字段属性" align="center" prop="fieldProp"/>
        <el-table-column label="字段属性类型" align="center" prop="fieldPropType">
          <template slot-scope="scope">
            {{ getDictValue(dict, 'field_prop_type', scope.row.fieldPropType) }}
          </template>
        </el-table-column>
        <el-table-column label="字段选项数据" align="center" prop="fieldOptions"/>
        <el-table-column label="字段定义类型 1固定列  2动态列" align="center" prop="fieldType"/>
        <!--        <el-table-column label="字段合并" align="center" prop="fieldMerge">
                  <template slot-scope="scope">
                    {{ scope.row.fieldMerge === 0 ? '否' : '是' }}
                  </template>
                </el-table-column>-->
        <el-table-column label="是否列表显示" align="center" prop="fieldShow">
          <template slot-scope="scope">
            {{ scope.row.fieldShow === 0 ? '否' : '是' }}
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime"/>

        <el-table-column label="操作" width="220px" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" v-if="scope.row.fieldType !== 1" v-has-permi="['field:config:remove']"
                       @click="handleDelete(scope.row)">删除
            </el-button>
            <el-button type="text" v-has-permi="['field:config:edit']"
                       @click="handleUpdate(scope.row)">修改
            </el-button>
            <el-button type="text" icon="el-icon-top" v-show="scope.$index !== 0" v-has-permi="['field:config:order']"
                       @click="moveUpRow(scope.$index, headerList)">上移
            </el-button>
            <el-button type="text" icon="el-icon-bottom" v-show="scope.$index !== (headerList.length - 1)"
                       v-has-permi="['field:config:order']" @click="moveDownRow(scope.$index, headerList)">下移
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--      <div class="add_row" @click="addRow(HEADER_PARAMS,fieldForm.headerList)">
              <el-button type="text" icon="el-icon-plus">新增属性</el-button>
            </div>-->
    </div>


    <el-dialog :title="title" :visible.sync="open" width="40%" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="字段名称" prop="fieldName">
          <el-input v-model="form.fieldName" placeholder="请输入字段名称"/>
        </el-form-item>
        <el-form-item label="字段属性类型" prop="fieldPropType">
          <el-radio-group v-model="form.fieldPropType" placeholder="请选择字段属性类型">

            <el-radio v-for="item in dict.type['field_prop_type']" :key="item.value" :label="item.value">
              {{ item.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="字段选项数据" prop="fieldOptions" v-show="form.fieldPropType==='select'">
          <div v-for="(item,index) in optionValues" :key="index" style="display:flex;margin-top: 8px">
            <el-input v-model="item.value" placeholder="请输入数据"/>
            <el-button style="width: 35px;margin-left: 10px" icon="el-icon-minus" circle
                       @click="operateOptions(2,index)"></el-button>

          </div>
          <el-button style="margin-top: 8px" icon="el-icon-plus" circle @click="operateOptions(1)"></el-button>
        </el-form-item>

        <!-- <el-form-item label="字段合并" prop="fieldMerge">
          <el-radio-group v-model="form.fieldMerge" placeholder="请选择是否字段合并">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item> -->
        <el-form-item label="是否列表显示" prop="fieldShow">
          <el-radio-group v-model="form.fieldShow" placeholder="请选择是否字段显示">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="字段备注" prop="fieldRemark">
          <el-input v-model="form.fieldRemark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import {listConfig, addConfig, updateConfig, delConfig, getConfig, updateSortConfig} from "@/api/biz/fieldConfig";

const HEADER_PARAMS = [
  {
    prop: 'fieldName',
    propAlias: '字段名称',
    required: true,
    rules: [
      {required: true, message: '所在字不能为空', trigger: 'blur'}
    ],
    type: 'input',
    placeholder: ''
  }, {
    prop: 'fieldPropType',
    propAlias: '字段类型',
    required: true,
    rules: [
      {required: true, message: '所在字不能为空', trigger: 'blur'}
    ],
    type: 'radio',
    options: [{key: 'input', value: 'input'}],
    placeholder: ''
  }, {
    prop: 'fieldOptions',
    propAlias: '选项数据',
    required: true,
    rules: [
      {required: true, message: '所在字不能为空', trigger: 'blur'}
    ],
    type: 'input',
    placeholder: ''
  }, {
    prop: 'fieldMerge',
    propAlias: '是否合并',
    required: false,
    type: 'input',
    placeholder: ''
  }, {
    prop: 'fieldShow',
    propAlias: '是否列表显示',
    required: false,
    type: 'input',
    placeholder: ''
  }, {
    prop: 'createTime',
    propAlias: '创建时间',
    required: true,
    rules: [
      {required: true, message: '所在字不能为空', trigger: 'blur'}
    ],
    type: 'input',
    placeholder: ''
  }
]

export default {
  name: 'index',
  dicts: ['field_prop_type'],
  data() {
    return {
      HEADER_PARAMS,
      loading: true,
      title: '新增',
      open: false,
      queryParams: {},
      form: {
        fieldShow: 1, // 显示/隐藏字段展示页面控件 （1显示，0隐藏） 默认1显示展
      },
      rules: {
        fieldName: [
          {required: true, message: '字段名称不能为空', trigger: 'blur'}
        ],
        fieldPropType: [
          {required: true, message: '字段属性类型不能为空', trigger: 'blur'}
        ],
      },
      headerList: [],
      spanMergeObj: {},
      fieldHeaders: [],
      optionValues: []

    }
  },
  created() {
    this.getList()
    // this.getListDataForRowAndColumn(this.data)

  },

  methods: {
    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    getList() {
      this.loading = true;
      listConfig(this.queryParams).then(response => {
        this.headerList = response.data;
        this.loading = false;
      });
    },
    getDictValue(dicts, type, key) {
      console.log(this.dict)
      //      this.dict.type['field_prop_type'][scope.row.fieldPropType]
      return this.dict.label[type][key]
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()

      this.open = true
      this.title = '添加字段'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      this.optionValues = []
      getConfig(id).then(response => {
        this.form = response.data
        if (this.form.fieldOptions) {
          let arr = this.form.fieldOptions.split(',')
          this.optionValues = arr.map(item => {
            return {
              value: item
            }
          })
        }
        this.open = true
        this.title = '修改字段'
      })
    },
    /** 提交按钮 */
    submitForm: function () {
      //默认动态列
      this.form.fieldType = 2
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.fieldOptions = this.optionValues.map(item => item.value).filter(val => val != null & val != '').join(',');
          console.log(this.form)
          if (this.form.id != null) {
            updateConfig(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addConfig(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    cancel() {
      this.open = false
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      if (row.fieldType === 1) {
        return
      }
      this.$modal.confirm('确认删除字段' + row.fieldName + '？').then(function () {
        return delConfig(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    addRow(props, list) {
      let obj = {}
      if (this.paramId) {
        obj.paramId = this.form.id
      }
      props.forEach(item => {
        obj[item.prop] = null
      })
      list.push(obj)
    },
    removeRow(index, list) {
      this.$modal.confirm('您确认删除字段？').then(value => {
        list.splice(index, 1)
      }).catch(reason => {
      })
    },
    moveUpRow(index, headerList) {
      let list = JSON.parse(JSON.stringify(headerList))
      if (index > 0) {
        let upDate = list[index - 1]
        list.splice(index - 1, 1)
        list.splice(index, 0, upDate)

        updateSortConfig({
          ids: list.map(item => item.id)
        }).then(value => {
          this.getList()
        })
      } else {
        this.$message.warning('已经是第一条了！')
        return false
      }

    },
    moveDownRow(index, headerList) {
      let list = JSON.parse(JSON.stringify(headerList))

      if ((index + 1) === list.length) {
        this.$message.warning('已经是最后一条，不可下移')
      } else {
        console.log(index)
        let downDate = list[index + 1]
        list.splice(index + 1, 1)
        list.splice(index, 0, downDate)

        updateSortConfig({
          ids: list.map(item => item.id)
        }).then(value => {
          this.getList()
        })
      }

    },
    /** 导出模板按钮操作 */
    handleExport() {
      this.downloadPostJsonBody('/system/field/config/download', {}, `字段模板_${new Date().getTime()}.xlsx`)
    },

    getListDataForRowAndColumn(data) {
      let mergeObj = this.spanMergeObj
      let headers = this.fieldHeaders.filter(item => item.fieldMerge === 1)

      for (let i = 0; i < data.length; i++) {
        if (i === 0) {
          // 如果是第一条记录（即索引是0的时候），向数组中加入１
          //判定需要合并的列
          for (let j = 0; j < headers.length; j++) {
            let field = headers[j]
            mergeObj[field.prop] = {
              pos: 0,
              rows: [1]
            }
          }
        } else {
          for (let j = 0; j < headers.length; j++) {
            let field = headers[j]
            // 如果prop的值相等就累加，并且push 0
            if (data[i][field.prop] === data[i - 1][field.prop]) {
              mergeObj[field.prop].rows[mergeObj[field.prop].pos] += 1
              mergeObj[field.prop].rows.push(0)
            } else {
              // 不相等push 1
              mergeObj[field.prop].rows.push(1)
              mergeObj[field.prop].pos = i

            }
          }

        }
      }
      console.log(mergeObj)

    },
    objectSpanMethod({row, column, rowIndex, columnIndex}) {
      let mergeObj = this.spanMergeObj
      let prop = column.property
      if (!prop) {
        return;
      }
      if (mergeObj[prop] && columnIndex > 0) {
        if (mergeObj[prop].rows[rowIndex]) {
          let rowNum = mergeObj[prop].rows[rowIndex]
          return {
            rowspan: rowNum,
            colspan: rowNum > 0 ? 1 : 0
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0
          }
        }

      }

    },
    operateOptions(type, index) {
      if (type === 1) {
        this.optionValues.push({
          value: ''
        })
      } else {
        this.optionValues.splice(index, 1)
      }
    }

  }
}
</script>

<style lang="scss" scoped></style>
