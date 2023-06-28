<template>
  <div class="top-right-btn">
    <div style="display: flex;align-items: center">
      <el-tooltip class="item" effect="dark" content="中英切换" placement="top">
        <svg-icon class-name="point" style="font-size: 20px;margin-right: 10px" icon-class="fanyi"
                  @click="changeLang"></svg-icon>
      </el-tooltip>
      <slot name="customBtn"></slot>
      <!--      <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top">
              <el-button size="mini" circle icon="el-icon-search" @click="toggleSearch()"/>
            </el-tooltip>-->
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <el-button size="mini" circle icon="el-icon-refresh" @click="refresh()"/>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="参数选择" placement="top">
        <el-button size="mini" circle icon="el-icon-menu" @click="showColumn()"/>
      </el-tooltip>
    </div>
    <!--    v-dialog-drag="true"-->
    <custom-dialog v-dialog-drag="true" :visible.sync="open" width="70%" title="列配置" :before-close="cancelColumnDialog">
      <div ref="dialog_content">
        <div class="col_group">
          <el-select v-model="groupId" filterable placeholder="请选择组" @change="changeGroup">
            <el-option-group
              v-for="(type,index) in paramFormatGroup"
              :key="index"
              :label="type.type==0?'个人组':'全局组'">
              <el-option
                v-for="(item,index) in type.children"
                :key="index+item.formatName"
                :label="item.formatName"
                :value="item.id">
                <span style="">{{ item.formatName }}</span>
                <el-tag style="margin-left: 10px">{{ type.type == 0 ? '个人组' : '全局组' }}</el-tag>
              </el-option>
            </el-option-group>

          </el-select>

          <el-button style="margin-left: 10px" icon="el-icon-plus" @click="newGroup" size="mini">新建</el-button>
          <el-button icon="el-icon-copy-document" size="mini" @click="copyGroup">复制</el-button>
          <el-button icon="el-icon-delete" size="mini" @click="deleteGroup">删除</el-button>
        </div>
        <el-divider></el-divider>
        <div>
          <div style="padding-bottom: 16px">
            <span>组名：</span>
            <el-input style="width: 260px" v-model="form.formatName" placeholder="请输入组名"></el-input>
          </div>
          <drag-transfer :left-original-list="leftCols"
                         :right-original-list="rightCols"
                         :global-group="globalGroup"
                         :admin="admin"
                         @change="changeColumns"
                         :default-props="{ label: 'paramName', key: 'paramId',}"></drag-transfer>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="!globalGroup || admin" @click="confirmColumn">确 定</el-button>
        <el-button @click="cancelColumnDialog">取 消</el-button>
      </div>
    </custom-dialog>

    <el-dialog :title="title" :visible.sync="tipShow" append-to-body
               :close-on-click-modal="false"
               width="70%"
    >
      <div ref="dialog_content">
        <el-form ref="settingForm" :model="form" label-width="80px" :rules="rules">
          <el-form-item label="参数组名" prop="width">
            <el-input style="width: 260px" v-model="form.formatName" placeholder="请输入组名"></el-input>
          </el-form-item>
          <el-form-item v-if="admin" label="是否全局组" prop="fixed">
            <el-switch v-model="form.formatType"
                       :active-value="1"
                       :inactive-value="0"
                       active-text="全局组"></el-switch>
          </el-form-item>

        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmCreate">确 定</el-button>
        <el-button @click="cancelCreate">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import DragTransfer from "@/components/RightToolCustom/DragTransfer";
import CustomDialog from "@/components/RightToolCustom/CustomDialog";
import {listParamFormat, addParamFormat, editParamFormat, deleteParamFormat, listAllParam} from "@/api/biz/paramFormat";
import {getInfo} from "@/api/login";

export default {
  name: "RightToolCustom",
  components: {DragTransfer, CustomDialog},
  data() {
    return {
      // 显隐数据
      value: [],

      // 是否显示弹出层
      open: false,
      paramFormatGroup: [],
      paramFormatGroupList: [],
      leftCols: [],
      rightCols: [],
      queryParam: {
        tableName: '',
      },
      groupId: null,
      form: {
        formatName: null,
        id: null,
        paramFormatDetailList: [],
        formatType: 0
      },
      historyForm: {},
      rules: {},
      settingShow: false,
      currSelectColumns: [],
      // 弹出层标题
      title: "",
      tipShow: false,
      admin: false,
      globalGroup: false,
    };
  },
  props: {
    showSearch: {
      type: Boolean,
      default: true,
    },
    tableName: {
      type: String
    }
    /* columns: {
       type: Array,
     },*/
  },

  created() {
    //由父组件触发 数据获取
    //this.getData()
    getInfo().then(res => {
      const user = res.user
      this.admin = user.admin
    })
  },
  methods: {
    getData(paramFormatId) {
      console.log(paramFormatId)
      //查询用户自定义组
      this.getParamFormat(paramFormatId)
      //查询所有参数列
      this.queryParam.tableName = this.tableName
      listAllParam(this.queryParam).then(res => {
        let right = res.data
        right.sort((a, b) => {
          return a.paramName.length - b.paramName.length
        })
        this.rightCols = right
      })

    },
    getParamFormat(paramFormatId) {
      this.queryParam.tableName = this.tableName
      this.paramFormatGroup = []
      listParamFormat(this.queryParam).then(res => {
        this.paramFormatGroupList = res.data
        this.paramFormatGroup = this.groupFunc(res.data, 'formatType')

        if (paramFormatId) {

          let find = this.paramFormatGroupList.filter(v => v.id == paramFormatId)[0]
          if (find) {
            this.form = find
            //历史
            this.historyForm = JSON.parse(JSON.stringify(this.form))
            this.groupId = paramFormatId
            this.leftCols = JSON.parse(JSON.stringify(find.paramFormatDetailList))
            this.currSelectColumns = this.leftCols
          }
        }
      }).finally(() => {
      })
    },
    groupFunc(list, groupKey = 'type', children = 'children') {
      let map = {}
      //保持列原来顺序
      list.forEach(item => {
        //分组
        let list = map[item[groupKey]] ? map[item[groupKey]] : []
        list.push(item)
        map[item[groupKey]] = list
      })

      let groupList = Object.keys(map).map(k => {
        let obj = {}
        obj['type'] = k
        obj[children] = map[k]
        return obj
      })
      return groupList
    },
    changeLang() {
      this.$emit("changeLang");

    },
    // 搜索
    toggleSearch() {
      this.$emit("update:showSearch", !this.showSearch);
    },
    // 刷新
    refresh() {
      let param = null
      console.log(this.currSelectColumns)
      if (this.currSelectColumns && this.currSelectColumns.length > 0) {
        param = JSON.parse(JSON.stringify({
          rowKeyList: this.currSelectColumns.map(v => v.paramName),
          selectColumnsList: this.currSelectColumns,
          paramFormatId: this.groupId,
        }))
      }
      this.$emit("queryTable", param);
    },
    // 打开显隐列dialog
    showColumn() {
      this.open = true;
    },

    changeGroup(val) {
      console.log(this.form.id, val)
      if (this.groupId) {
        let find = this.paramFormatGroupList.filter(v => v.id === val)[0]
        this.form = find
        this.historyForm = JSON.parse(JSON.stringify(this.form))

        console.log(find, this.form)

        //找出所在列
        this.leftCols = JSON.parse(JSON.stringify(find.paramFormatDetailList))
        this.currSelectColumns = this.leftCols
        console.log(this.currSelectColumns)

        //是否全局组
        this.globalGroup = find.formatType === 1
      }
    },
    newGroup() {
      this.tipShow = true
      this.title = '新建组'
      this.groupId = null
      this.form = {
        formatName: '临时组名请修改',
        formatType: 0
      }
      this.leftCols = []
      this.currSelectColumns = this.leftCols
      this.globalGroup = false

    },
    deleteGroup() {
      if (!this.form.id) {
        this.$modal.alertWarning("请选择组后删除")
        return
      }
      console.log(this.form)
      if (this.form.formatType == 1 && !this.admin) {
        this.$modal.alertWarning("全局组不可删除")
        return
      }

      this.$modal.confirm("你确定删除当前组吗？").then(value => {
        console.log(value)
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        deleteParamFormat(this.form.id).then(value1 => {
          this.form = {}
          this.groupId = null
          this.leftCols = []
          this.currSelectColumns = this.leftCols
          this.getParamFormat()
          this.$modal.msgSuccess("删除成功！")
          loading.close()

        })
      }).catch(reason => {
      })
    },
    copyGroup() {


      if (!this.form.id) {
        this.$modal.alertWarning("请选择组后复制")
      } else {
        this.tipShow = true
        this.title = '新建组'

        this.globalGroup = false
        let find = this.paramFormatGroupList.filter(v => v.id === this.form.id)[0]
        this.form = JSON.parse(JSON.stringify(find))
        this.form.formatName = find.formatName + '-副本'
        this.form.id = null
        //转化为个人组
        this.form.formatType = 0
        this.groupId = null
        this.leftCols = find.paramFormatDetailList ? JSON.parse(JSON.stringify(find.paramFormatDetailList)) : []
        this.currSelectColumns = this.leftCols

      }
    },
    confirmCreate() {
      this.tipShow = false
      this.title = ''
    },
    cancelCreate() {
      this.tipShow = false
      this.title = ''
    },
    changeColumns(obj) {
      console.log('changed。。')
      console.log(obj)
      this.currSelectColumns = obj.left
      console.log('是否对等', this.currSelectColumns === this.leftCols)
    },
    confirmColumn() {
      console.log('是否对等', this.currSelectColumns === this.leftCols)
      console.log('是否对等', this.form.formatName === this.historyForm.formatName)

      //如果是选择已存在的组也要校验
      if (!this.form.formatName) {
        this.$modal.msgWarning('请输入自定义组名')
        return;
      }
      if (this.currSelectColumns == null || this.currSelectColumns.length === 0) {
        this.$modal.msgWarning('请至少选择一个列')
        return;
      }
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      this.form.paramFormatDetailList = this.currSelectColumns
      let data = JSON.parse(JSON.stringify(this.form))
      data.tableName = this.tableName
      console.log(data)
      if (this.form.id) {
        console.log('???', !(this.currSelectColumns === this.leftCols) || !(this.historyForm.formatName === this.form.formatName))
        if (!(this.currSelectColumns === this.leftCols) || !(this.historyForm.formatName === this.form.formatName)) {
          editParamFormat(data).then(res => {
            this.open = false
            //新id
            this.groupId = res.data
            this.form.id = res.data

            this.refresh()

            //this.cancelColumnDialog()
            loading.close();
            this.$modal.msgSuccess("切换成功！")
          }).catch(() => {
            loading.close();

          }).finally(() => {
            //this.getParamFormat()

          })
        } else {
          this.refresh()
          this.open = false
          loading.close();

        }

      } else {
        addParamFormat(data).then(res => {
          this.open = false
          this.groupId = res.data
          this.form.id = res.data
          this.refresh()
          this.cancelColumnDialog()
          loading.close();
          this.$modal.msgSuccess("切换成功！")

        }).catch(() => {
          loading.close();

        }).finally(() => {
          this.getParamFormat()
        })
      }
    },
    cancelColumnDialog() {
      this.open = false
      /*this.groupId = null
      this.form = {
        formatName: null,
        id: null,
        paramFormatDetailList: []
      }
      this.currSelectColumns = []
      this.leftCols = []*/
    },

  },

};
</script>
<style lang="scss" scoped>
::v-deep .el-transfer__button {
  border-radius: 50%;
  padding: 12px;
  display: block;
  margin-left: 0px;
}

::v-deep .el-transfer__button:first-child {
  margin-bottom: 10px;
}

::v-deep .el-transfer-panel {
  width: 260px;
}

::v-deep .el-transfer-panel__body {
  height: 280px;

  .el-transfer-panel__list.is-filterable {
    height: 280px;

  }
}

::v-deep .el-divider--horizontal {
  margin: 16px 0;
}

.point:hover{
  cursor: pointer;
}
</style>
