<template>
  <div>
    <div class="transfer-tree"
         @click.stop=""
         :style="{'flex-direction':direction==='right'?'':'row-reverse'}">
      <div class="transfer-panel">
        <div class="transfer-panel-header">
          <el-checkbox
            v-model="leftAllChecked"
            :disabled="!(leftDataList && leftDataList.length)"
            :indeterminate="isIndeterminateLeft"
            @change="handleCheckAllChangeLeft">{{ leftTitle }}
          </el-checkbox>
          <span class="transfer-panel-ratio">{{ leftCheckedList.length }}/{{ leftDataList.length }}</span>

        </div>
        <div class="transfer-panel-body">
          <el-input v-model="leftWord" placeholder="请输入关键字"
                    prefix-icon="el-icon-search"
                    style="margin-bottom: 8px"></el-input>
          <draggable
            :list="leftDataList"
            :group="globalGroup?(admin?componentsGroup:'globalGroup'):componentsGroup"
            @end="onEndLeft"
            class="left_col"
            chosenClass="chosen"
            ghostClass="ghostClass"
            forceFallback="true"
            draggable=".components-item"
            style="height: 300px;display: block;;overflow: hidden auto"
          >
            <div
              :draggable="true"
              v-for="(item, index) in leftDataList"
              :key="`left_${item[defaultProps.key]}_${index}`"
              :class="{'components-item':!globalGroup || admin}"
              class="left_item"
              v-show="leftWord==null || item[defaultProps.label].toLowerCase().indexOf(leftWord.toLowerCase())>-1"
            >
              <div class="check_box" @click="handleCheckLeft(item)">
                <input class="check" type="checkbox" :checked="item.checked" @click.stop="handleCheckLeft(item)">
                <span
                  :class="{'noExist':judgeExist(item)}"
                  class="check_label"> {{ item[defaultProps.label] }}<i style="margin-left: 10px"
                                                                        class="el-icon-rank"
                                                                        title="拖拽排序"></i></span>
              </div>
              <div class="right_shortcut" v-if="!globalGroup || admin">
                <div @click.stop="changeFix($event,item)" class="fixed_check" :key="item.id"
                     :class="{'is-active':item.fixed===true}">
                  <span>固定</span>
                </div>
                <div style="margin-right: 20px;cursor: pointer" @click="setting(item,index)">
                  <i class="el-icon-setting"></i>
                </div>
                <div style="margin-right: 20px;cursor: pointer" @click="removeLeft(item,index)">
                  <i class="el-icon-close"></i>
                </div>

              </div>
            </div>
            <el-empty key="empty" description="暂无数据" v-if="leftDataList && leftDataList.length<=0"></el-empty>
          </draggable>
          <!--        <el-tree
                    ref="leftTree"
                    show-checkbox
                    check-on-click-node
                    default-expand-all
                    :node-key="defaultProps.key"
                    :data="leftDataList"
                    :props="defaultProps"
                    @check="handleCheckLeft">
                  </el-tree>-->
        </div>
      </div>
      <div class="transfer-buttons">
        <el-button
          class="mb8"
          style="padding: 16px 24px;margin-bottom: 16px"
          icon="el-icon-arrow-left"
          :disabled="direction === 'right'?!(rightCheckedList && rightCheckedList.length) : !(leftCheckedList && leftCheckedList.length)"
          @click="handleRightToLeftChange"></el-button>
        <el-button
          type="primary"
          style="padding: 16px 24px"
          icon="el-icon-arrow-right"
          :disabled="direction === 'right'?!(leftCheckedList && leftCheckedList.length):!(rightCheckedList && rightCheckedList.length) "
          @click="handleLeftToRightChange"></el-button>
      </div>
      <div class="transfer-panel">
        <div class="transfer-panel-header">
          <el-checkbox
            v-model="rightAllChecked"
            :disabled="!(rightDataList && rightDataList.length)"
            :indeterminate="isIndeterminateRight"
            @change="handleCheckAllChangeRight">{{ rightTitle }}
          </el-checkbox>
          <!-- 右侧数据量/限制最大可保存数据量 -->
          <div>
            <span style="margin-right: 10px" @click="switchDirection">
            <svg-icon style="font-size: 12px" icon-class="switchDirection"></svg-icon>
          </span>
            <span class="transfer-panel-ratio">{{ rightCheckedList.length }}/{{ rightDataList.length }}</span>
          </div>
        </div>
        <div class="transfer-panel-body">
          <el-input v-model="rightWord" placeholder="请输入关键字"
                    prefix-icon="el-icon-search"
                    style="margin-bottom: 8px"></el-input>
          <draggable
            :delay="0"
            :list="rightDataList"
            :sort="false"
            :group="{ name: 'componentsGroup', pull: 'clone', put: false,sort:false }"
            draggable=".components-item"
            chosenClass="chosen"
            ghostClass="ghostClass"
            forceFallback="true"
            @start="onRightStart"
            @end="onEndRight"
            :clone="onCloneRight"
            class="right_col"
            style="height: 300px;display: block;overflow: hidden auto"
          >
            <div class="components-item"
                 v-show="rightWord==null || item[defaultProps.label].toLowerCase().indexOf(rightWord.toLowerCase())>-1"
                 v-for="(item, index) in rightDataList"
                 :key="`right_${item[defaultProps.key]}_${index}`">
              <div class="check_box" @click="handleCheckRight(item)">
                <input type="checkbox" :checked="item.checked" @click.stop="handleCheckRight(item)">
                <span class="check_label"> {{ item[defaultProps.label] }}</span>
              </div>

            </div>
            <el-empty key="empty" description="暂无数据" v-if="rightDataList && rightDataList.length<=0"></el-empty>
          </draggable>
        </div>
      </div>


      <el-dialog title="列配置" :visible.sync="settingShow" append-to-body
                 :close-on-click-modal="false"
                 width="70%"
      >
        <div ref="dialog_content">

          <el-form ref="settingForm" :model="form" label-width="80px" :rules="rules">
            <el-form-item label="列名">
              <el-tag> {{ tempSetting[defaultProps.label] }}</el-tag>
            </el-form-item>
            <el-form-item label="列宽度" prop="width">
              <el-input v-model="form.width" style="width: 200px">
                <template slot="append">px</template>
              </el-input>

            </el-form-item>
            <el-form-item label="固定列" prop="fixed">
              <el-switch v-model="form.fixed" active-text="固定"></el-switch>
            </el-form-item>

<!--            <el-form-item label="显示长度" prop="total" style="width: 200px">
              <el-input v-model="form.total" :min="1" :step="1" step-strictly></el-input>
            </el-form-item>-->

            <el-form-item label="显示精度" prop="precision" style="width: 200px">
              <el-input v-model="form.precision" :min="0" :step="1" step-strictly></el-input>

            </el-form-item>
            <el-form-item label="显示颜色" prop="color" style="width: 200px">
              <el-color-picker
                v-model="form.color"
                :predefine="predefineColors"
              >
              </el-color-picker>

            </el-form-item>
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="confirmSetting">确 定</el-button>
          <el-button @click="cancelSetting">取 消</el-button>
        </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import draggable from 'vuedraggable';

export default {
  name: 'DragTransfer',
  components: {
    draggable,
  },
  props: {
    // tree的默认结构
    defaultProps: {
      type: Object,
      //required: true,
      default: () => ({
        children: 'children',
        label: 'label',
        key: 'key',
        parentKey: 'parent', // 这个属性不是 tree组件需要的，是子节点数据中记录父节点标识的属性
      }),
    },
    // left 原始数据
    leftOriginalList: {
      type: Array,
      default: () => [],
    },
    // right 原始数据
    rightOriginalList: {
      type: Array,
      default: () => [],
    },
    // 最大可保存数据量
    maxLimitCount: {
      type: Number,
      default: 0,
    },
    // left 标题
    leftTitle: {
      type: String,
      default: '全选（已选列）',
    },
    // right 标题
    rightTitle: {
      type: String,
      default: '全选（待选列)',
    },
    globalGroup: {
      type: Boolean,
      default: false,
    },
    admin: {
      type: Boolean,
      default: false,
    }
  },
  data() {
    const numberValid = (rule, val, callback) => {
      if (!val) {
        callback();
        // return true;
      } else {
        //const reg = /^\+?[1-9]\d*$/
        const reg = /^\d+$/
        if (reg.test(val)) {
          callback();
        } else {
          callback(new Error("请输入正确数值"));
        }
      }

    }
    return {
      optionsGroupName: 'componentsGroup',
      leftWord: null,
      leftAllChecked: false, // left 全选checkbox
      leftDataList: [], // left 所有数据
      leftCheckedList: [], // left 选中的数据
      leftCheckedKeyList: [],//left 选中的 key list => 绑定在 el-checkbox-group上的 list
      isIndeterminateLeft: false, //left

      rightWord: null,
      rightAllChecked: false, // right 全选checkbox
      rightDataList: [], // right 所有数据
      rightCheckedList: [], // right 选中的数据 =>rightCheckedKeyList对应的 对象数组
      rightCheckedKeyList: [], // right 选中的 key list => 绑定在 el-checkbox-group上的 list
      isIndeterminateRight: false,
      drag: false,
      settingShow: false,
      tempSetting: {},
      rules: {
        width: [
          {validator: numberValid, trigger: "blur"}
        ],
        total: [
          {validator: numberValid, trigger: "blur"}
        ],
        precision: [
          {validator: numberValid, trigger: "blur"}
        ],
      },
      form: {
        width: null,
        total: null,
        precision: null,
        fixed: false,
      },
      fixed: false,
      predefineColors: [
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577'
      ],
      checkAll: false,
      //默认全参数在右侧
      direction: 'right',
      componentsGroup: 'componentsGroup'
    };
  },
  // 初始化
  watch: {
    leftOriginalList: {
      immediate: true,
      deep: true,
      handler(newVal) {
        this.leftDataList = JSON.parse(JSON.stringify(newVal));
        //给所有列加上额外配置
        this.leftDataList.forEach(value => {
          this.$set(value, 'fixed', value.showConfig ? JSON.parse(value.showConfig).fixed : false)
        })
        this.leftCheckedList = [];
        this.leftAllChecked = false;
        this.isIndeterminateLeft = false;
      },
    },
    rightOriginalList: {
      immediate: true,
      deep: true,
      handler(newVal) {
        this.rightDataList = JSON.parse(JSON.stringify(newVal));
        this.rightDataList.forEach(value => {
          this.$set(value, 'checked', false)
        })
        console.log(this.rightDataList)
        this.rightCheckedList = [];
        this.rightCheckedKeyList = [];
        this.rightAllChecked = false;
        this.isIndeterminateRight = false;
      },
    },
  },
  computed: {
    // left 所有子节点数据的数量
    /* leftDataTotal() {
       let count = 0;
       this.leftDataList.forEach((v) => {
         if (v[this.defaultProps.children]) {
           count += v[this.defaultProps.children].length;
         }
       });
       return count;
     },*/
  },
  created() {
  },
  methods: {
    filterMethod(query, item) {
      console.log(query, item)
      return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1;
    },
    // 选择——left
    handleCheckLeft(item) {
      this.$set(item, "checked", !item.checked)
      this.leftCheckedList = this.leftDataList.filter(item => item.checked)
      this.leftCheckedKeyList = this.leftCheckedList.map(value => value[this.defaultProps.key])

      const checkedCount = this.leftCheckedList.length;
      this.leftAllChecked = checkedCount === this.leftDataList.length;
      this.isIndeterminateLeft = checkedCount > 0 && checkedCount < this.leftDataList.length;

    },
    // 全选——left
    handleCheckAllChangeLeft(val) {
      this.leftDataList
        .filter(item => this.leftWord == null || item[this.defaultProps.label].toLowerCase().indexOf(this.leftWord.toLowerCase()) > -1)
        .map(item => {
          item.checked = val
        })
      this.isIndeterminateLeft = false;

      let checkedKey = []
      let checkedList = []

      if (val) {
        this.leftDataList
          .filter(item => this.leftWord == null || item[this.defaultProps.label].toLowerCase().indexOf(this.leftWord.toLowerCase()) > -1)
          .forEach((v) => {
            checkedKey.push(v[this.defaultProps.key])
            checkedList.push(v)
          })
      }
      this.leftCheckedKeyList = checkedKey
      this.leftCheckedList = checkedList

    },

    // 选择——right
    handleCheckRight(item) {
      this.$set(item, "checked", !item.checked)

      this.rightCheckedList = this.rightDataList.filter(item => item.checked)
      this.rightCheckedKeyList = this.rightCheckedList.map(value => value[this.defaultProps.key])

      const checkedCount = this.rightCheckedList.length;
      this.rightAllChecked = checkedCount === this.rightDataList.length;
      this.isIndeterminateRight = checkedCount > 0 && checkedCount < this.rightDataList.length;

      // 手动组织对象数组
      /*const checkedCount = val.length;
      this.rightAllChecked = checkedCount === this.rightDataList.length;
      this.isIndeterminateRight = checkedCount > 0 && checkedCount < this.rightDataList.length;
      // 手动组织对象数组
      this.rightCheckedList = this.rightDataList.filter((v) => (val.includes(v[this.defaultProps.key])));
      this.rightCheckedKeyList = val ? val : [];*/

    },

    // 全选——right
    handleCheckAllChangeRight(val) {
      this.rightDataList
        .filter(item => this.rightWord == null || item[this.defaultProps.label].toLowerCase().indexOf(this.rightWord.toLowerCase()) > -1)
        .map(item => {
          item.checked = val
        })
      this.isIndeterminateRight = false;

      let checkedKey = []
      let checkedList = []
      if (val) {
        this.rightDataList
          .filter(item => this.rightWord == null || item[this.defaultProps.label].toLowerCase().indexOf(this.rightWord.toLowerCase()) > -1)
          .forEach((v) => {
            checkedKey.push(v[this.defaultProps.key])
            checkedList.push(v)
          })
      }
      this.rightCheckedKeyList = checkedKey
      this.rightCheckedList = checkedList

    },
    // 右边转 左边 传递 right => left
    handleRightToLeftChange() {
      if (this.globalGroup && !this.admin) {
        this.$modal.msgWarning('全局组不可编辑')
        return
      }
      if (this.direction === 'right') {
        this.handleRightToLeft()
      } else {
        this.handleLeftToRight()
      }

    },
    // 传递 left => right
    handleLeftToRightChange() {
      if (this.globalGroup && !this.admin) {
        this.$modal.msgWarning('全局组不可编辑')
        return
      }
      if (this.direction === 'right') {
        this.handleLeftToRight()
      } else {
        // left +
        // right -
        this.handleRightToLeft()
      }

    },
    handleRightToLeft() {
      // left +
      // right -
      console.log(this.rightCheckedList)

      let checkData = []
      this.rightCheckedList.forEach(v => {
        v.checked = false
        let item = JSON.parse(JSON.stringify(v))
        //给所有列加上额外配置
        item.fixed = false
        checkData.push(item)
      })
      let currLeftData = [
        ...this.leftDataList,
        ...checkData
      ]
      //去重left
      const res = new Map();
      this.leftDataList = currLeftData.filter(arr => !res.has(arr[this.defaultProps.key]) && res.set(arr[this.defaultProps.key], arr));

      if (this.rightAllChecked) this.rightWord = null

      // 清空选中数组
      this.rightCheckedList = [];
      this.rightCheckedKeyList = [];
      // right 全选 => 直接取消
      this.rightAllChecked = false;
      this.isIndeterminateRight = false;

      // left 全选 => 原先没有选中/半选中=>不动，原先全选=>半选中 => 重新渲染一次 tree组件选中
      if (this.leftAllChecked && !this.isIndeterminateLeft) {
        this.leftAllChecked = false;
        this.isIndeterminateLeft = true;
      }

      // 传递当前数据分布
      this.$emit('change', {
        left: this.leftDataList,
        right: this.rightDataList,
      });
    },
    handleLeftToRight() {
      // right +
      // left -
      this.leftDataList = this.leftDataList.filter((v) => !(this.leftCheckedKeyList.includes(v[this.defaultProps.key])));

      if (this.leftAllChecked) this.leftWord = null
      // 清空选中数组
      this.leftCheckedList = [];
      this.leftCheckedKeyList = [];

      // left 全选 => 直接取消
      this.leftAllChecked = false;
      this.isIndeterminateLeft = false;


      // right 全选 => 原先没有选中/半选中=>不动，原先全选=>半选中
      if (this.rightAllChecked && !this.isIndeterminateRight) {
        this.rightAllChecked = false;
        this.isIndeterminateRight = true;
      }
      // 传递当前数据分布
      this.$emit('change', {
        left: this.leftDataList,
        right: this.rightDataList,
      });
    },
    onStart(evt) {
      console.log('start', evt)
      this.drag = true;
      return true;
    },
    onEndLeft(evt) {
      console.log('left end')
      console.log(evt)
      console.log(this.leftDataList)
      this.$emit('change', {
        left: this.leftDataList,
        right: this.rightDataList,
      });
      this.drag = false;
    },

    onRightStart(evt) {
      console.log('rightstart', evt)
    },
    onCloneRight(val) {
      let clone = JSON.parse(JSON.stringify(val))
      clone.checked = false
      return clone
    },
    onEndRight(evt) {
      console.log('right end')
      console.log(this.leftDataList, this.rightDataList)
      //去重left
      const res = new Map();
      this.leftDataList = this.leftDataList.filter(arr => !res.has(arr[this.defaultProps.key]) && res.set(arr[this.defaultProps.key], arr));
      //给所有列加上额外配置
      this.leftDataList.forEach(value => {
        value.fixed = value.showConfig ? JSON.parse(value.showConfig).fixed : false
        /*if(value.fixed ===undefined || value.fixed  ===null){
        }*/
      })
      let checkedCount = this.leftCheckedKeyList.length
      this.leftAllChecked = checkedCount > 0 && checkedCount === this.leftDataList.length;
      this.isIndeterminateLeft = checkedCount > 0 && checkedCount < this.leftDataList.length;

      // 传递当前数据分布
      this.$emit('change', {
        left: this.leftDataList,
        right: this.rightDataList,
      });
      this.drag = false;
    },
    removeLeft(item, index) {
      this.leftDataList.splice(index, 1)

      // 清空选中数组
      this.leftCheckedList = this.leftCheckedList.filter(v => v[this.defaultProps.key] !== item[this.defaultProps.key]);
      this.leftCheckedKeyList = this.leftCheckedKeyList.filter(v => v !== item[this.defaultProps.key]);

      // left 全选 => 直接取消
      let checkedCount = this.leftCheckedKeyList.length
      this.leftAllChecked = checkedCount > 0 && checkedCount === this.leftDataList.length;
      this.isIndeterminateLeft = checkedCount > 0 && checkedCount < this.leftDataList.length;

      // 传递当前数据分布
      this.$emit('change', {
        left: this.leftDataList,
        right: this.rightDataList,
      });

    },

    changeFix(event, item) {
      let fixed = item.fixed
      this.$set(item, 'fixed', !fixed)
      //增加fixed属性
      let showConfig = item.showConfig ? JSON.parse(item.showConfig) : {}
      showConfig.fixed = item.fixed
      this.$set(item, 'showConfig', JSON.stringify(showConfig))
      console.log(item)

      // 传递当前数据分布
      this.$emit('change', {
        left: this.leftDataList,
        right: this.rightDataList,
      });
      this.$forceUpdate()
    },
    setting(item, index) {
      this.settingShow = true
      this.tempSetting = item
      this.form = item.showConfig ? JSON.parse(item.showConfig) : this.form
    },
    confirmSetting() {
      console.log(this.$refs['settingForm'])
      this.$refs['settingForm'].validate((valid) => {
        if (valid) {
          this.$set(this.tempSetting, 'showConfig', JSON.stringify(this.form))
          this.$set(this.tempSetting, 'fixed', this.form.fixed)
          // 传递当前数据分布
          this.$emit('change', {
            left: this.leftDataList,
            right: this.rightDataList,
          });
          this.settingShow = false
          this.tempSetting = {}
          this.form = {
            width: null,
            total: null,
            precision: null
          }
          console.log(this.leftDataList)
        }
      })


    },
    cancelSetting() {
      this.settingShow = false
      this.tempSetting = {}
      this.form = {
        width: null,
        total: null,
        precision: null
      }
    },
    judgeExist(item) {
      return !this.rightDataList
        .find(value => value[this.defaultProps.label] === item[this.defaultProps.label])

    },
    switchDirection() {
      this.direction === 'right' ? this.direction = 'left' : this.direction = 'right'
    }

  },
};
</script>

<style lang="scss" scoped>
$color-border: #ebeef5;
$color-text: #303133;
.transfer-tree {
  display: flex;
  width: 100%;
  box-sizing: border-box;
  transition: all 1s ease-in-out;

  .components-item {
    cursor: move;

  }

  .components-item:hover {
    color: #409eff !important;

  }

  .transfer-panel {
    //width: 350px;
    //width: 100%;
    flex: 1;
    height: 100%;
    border-radius: 4px;
    border: 1px solid $color-border;

    .transfer-panel-header {
      display: flex;
      justify-content: space-between;
      height: 30px;
      line-height: 30px;
      border-radius: 3px 3px 0px 0px;
      padding: 0 12px;

      ::v-deep .el-checkbox {
        .el-checkbox__label {
          color: $color-text;
          font-size: 14px;
          padding-left: 8px;
        }
      }

      .transfer-panel-ratio {
        font-size: 12px;
        color: $color-text;
      }
    }

    .transfer-panel-body {
      height: 360px;
      padding: 10px 12px 0 12px;
      border-top: 1px solid $color-border;
      //overflow: auto;
      ::v-deep .el-checkbox {
        cursor: move;
      }

      .transfer-panel-filter {
        float: right;
        width: 170px;

        .el-checkbox__label {
          color: $color-text;
          font-size: 12px;
          padding-left: 8px;
        }

        .el-input__inner {
          height: 26px;
          border: none;
        }
      }

      ::v-deep .el-tree {
        color: $color-text;
        margin-bottom: 4px;

        .el-tree-node__content {
          height: 22px;
          margin-bottom: 8px;

          .el-tree-node__label {
            font-size: 12px;
          }
        }

        .el-tree-node__children {
          .el-tree-node__content {
            padding-left: 12px !important;
          }
        }

        .el-tree-node__expand-icon {
          margin-left: -6px;
        }
      }

      ::v-deep .el-checkbox-group {
        margin-bottom: 4px;

        .el-checkbox {
          display: block;
          line-height: 22px;
          color: $color-text;
          //margin-bottom: 8px;
          padding-top: 4px;
          padding-bottom: 4px;
          width: 100%;

          .el-checkbox__label {
            width: calc(100% - 5px);
            position: relative;
            font-size: 12px;
            padding-left: 8px;

            img {
              position: absolute;
              right: 0;
              top: 2px;
            }
          }
        }
      }
    }
  }

  .transfer-buttons {
    display: flex;
    justify-content: center;
    flex-flow: column;
    margin: 0 18px;

    .el-button {
      display: flex;
      justify-content: center;
      align-items: center;
      width: 32px;
      height: 24px;
      padding: 0;
      margin-left: 0;
    }
  }
}

::v-deep .el-empty {
  height: 60px;
  padding: 0;

  .el-empty__image {
    display: none;
  }

  .el-empty__description {
    margin: 0;
  }


}

.ghostClass {
  background-color: #409eff !important;
  color: black !important;
  opacity: 0.3 !important;
}

.chosen {
  //background-color: #409eff !important;

}

.left_item {
  display: flex;
  justify-content: space-between;
  cursor: move !important;
  align-items: center;
}

.dragClass {
  background-color: #383838 !important;
  opacity: 1 !important;
  box-shadow: none !important;
  outline: none !important;
  background-image: none !important;
}

.right_shortcut {
  padding-left: 20px;
  display: flex;
  align-items: center;
  font-size: 12px;
}

.fixed_check {
  width: 35px;
  padding-left: 4px;
  padding-right: 4px;
  background: #fff;
  border: 1px solid #dcdfe6;
  color: #606266;
  margin-right: 20px;
  cursor: pointer;
  border-radius: 4px;
  font-size: 12px;
}

.fixed_check.is-active {
  color: #fff;
  background-color: #409eff;
  border-color: #409eff;
}

.check {
  border: 1px solid #dcdfe6;
}

.check_box {
  display: flex;
  align-items: center;
  padding: 4px 0;
}

.check_label {
  font-variant: tabular-nums;
  font-feature-settings: 'tnum';
  -webkit-font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, Arial, sans-serif;
  --current-color: #409EFF;
  -webkit-box-direction: normal;
  word-break: break-all;
  white-space: nowrap;
  font-weight: 500;
  user-select: none;
  cursor: move;
  color: #303133;
  box-sizing: inherit;
  display: inline-block;
  position: relative;
  font-size: 12px;
  padding-left: 8px;
}

.noExist {
  color: #c03639 !important;
}

/*.sortable-chose{
  background-color: #cecece !important;
  opacity: 1 !important;
}

.sortable-ghost{
  background-color: #409eff !important;
  color: black !important;
}*/
</style>
