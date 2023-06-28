<template>
  <div style="padding: 16px">
    <div class="form_container">
      <div class="left_form">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
          <el-form-item label="设备">
            <el-select style="flex-basis: 65%" filterable multiple
                       clearable
                       collapse-tags
                       :filter-method="(query)=>filterCheckPerOptions(query,'equipment','equipmentNames')"
                       v-model="queryParams.equipmentNames" placeholder="全部">
              <el-option
                v-for="item in selectOptions.equipment.top100"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
              <el-option
                v-if="selectOptions.equipment.top100.length >= 100 && selectOptions.equipment.list.length > 100"
                value="tip" disabled>
                <span class="gray">--只展示前100条数据，更多数据请输入查询--</span>
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="机型">
            <el-select style="flex-basis: 65%" filterable multiple
                       clearable
                       collapse-tags
                       :filter-method="(query)=>filterCheckPerOptions(query,'model','models')"
                       v-model="queryParams.models" placeholder="全部">
              <el-option
                v-for="item in selectOptions.model.top100"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
              <el-option
                v-if="selectOptions.model.top100.length >= 100 && selectOptions.model.list.length > 100"
                value="tip" disabled>
                <span class="gray">--只展示前100条数据，更多数据请输入查询--</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="架次">
            <el-select style="flex-basis: 65%" filterable multiple
                       collapse-tags
                       clearable
                       :filter-method="(query)=>filterCheckPerOptions(query,'plane','planeNos')"
                       v-model="queryParams.planeNos" placeholder="全部">
              <el-option
                v-for="item in selectOptions.plane.top100"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
              <el-option
                v-if="selectOptions.plane.top100.length >= 100 && selectOptions.plane.list.length > 100"
                value="tip" disabled>
                <span class="gray">--只展示前100条数据，更多数据请输入查询--</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="阶段">
            <el-select style="flex-basis: 65%" filterable multiple
                       collapse-tags clearable
                       v-model="queryParams.stages" placeholder="全部">
              <el-option
                v-for="item in selectOptions.stage.stageList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="具备试验能力">
            <el-select style="flex-basis: 65%"
                       v-model="queryParams.experimentCapacity"
                       clearable>
              <el-option label="是" value="是"></el-option>
              <el-option label="否" value="否"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="送外检定">
            <el-select style="flex-basis: 65%"
                       clearable
                       v-model="queryParams.externalCalibration" placeholder="全部">
              <el-option label="是" value="是"></el-option>
              <el-option label="否" value="否"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <div class="right_btn">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button style="margin-right: 10px" icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
                <el-button  size="mini"   slot="reference"  icon="el-icon-setting">列设置</el-button>
              </el-popover>
            </div>
          </el-form-item>
        </el-form>
      </div>


    </div>
    <div class="list_container">


      <el-table v-loading="loading" :data="list" stripe border >
        <el-table-column type="index" label="序号"></el-table-column>
        <el-table-column :label="prop.fieldName" v-for="(prop) in headerList"
                         :render-header="renderHeader"
                         :show-overflow-tooltip="true"
                         :key="prop.fieldProp"
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

      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                  :limit.sync="queryParams.pageSize"
                  @pagination="getList"/>
    </div>


  </div>

</template>

<script>
import {listConfig} from "@/api/biz/fieldConfig";
import {listDataByEquipment} from "@/api/biz/dataList";
import {statisticAllequipment, statisticAllModel, statisticAllPlaneNo, statisticAreaList} from "@/api/biz/statistic";
import {getToken} from "@/utils/auth";

export default {
  name: 'dataFilter',
  data() {
    return {
      loading: true,
      searchOpen: false,
      type: 'afo',
      list: [],
      headerList: [],
      queryParams: {
        parameters: {},
        area: null,
        pageNum: 0,
        pageSize: 10,
      },
      // 总条数
      total: 0,
      selectOptions: {
        equipment: {
          list: [],
          top100: [],
        },
        model: {
          list: [],
          top100: [],
        },
        plane: {
          list: [],
          top100: [],
        },
        stage: {
          stageList: ['G1', 'G2', 'G3', 'G4', 'G5', 'G6', 'G7', 'G8', 'G9', 'G10', 'G11'],
          selectList: [],
        },

      },
      columnSetting: []

    }
  },
  created() {
    this.queryParams = this.$route.query
    this.type = this.$route.query.type
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
    let condition = JSON.parse(localStorage.getItem("condition"))
    this.queryParams = {
      ...this.queryParams,
      ...condition
    }

    statisticAllequipment().then(res => {
      this.selectOptions.equipment.list = res.data

      let top100 = this.selectOptions.equipment.list.slice(0, 100)
      this.selectOptions.equipment.top100 = [...top100, ...this.queryParams.equipmentNames.filter(item => !top100.includes(item))]

    })
    statisticAllModel().then(res => {
      this.selectOptions.model.list = res.data

      let top100 = this.selectOptions.model.list.slice(0, 100)
      this.selectOptions.model.top100 = [...top100, ...this.queryParams.models.filter(item => !top100.includes(item))]

    })
    statisticAllPlaneNo().then(res => {
      this.selectOptions.plane.list = res.data

      let top100 = this.selectOptions.plane.list.slice(0, 100)
      this.selectOptions.plane.top100 = [...top100, ...this.queryParams.planeNos.filter(item => !top100.includes(item))]

    })
    this.getList()
  },
  destroyed() {
    localStorage.removeItem("condition")
  },
  methods: {
    getList() {
      this.loading = true;

      if (this.type === 'afo') {
        listDataByEquipment(this.queryParams).then(response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        })
      } else if (this.type === 'part') {
        statisticAreaList(this.queryParams).then(response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        })
      }

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
      column.minWidth = realWidth<50?80:realWidth + 30 ; // 可能还有边距/边框等值，需要根据实际情况加上

      document.body.removeChild(span);
      return h('span', column.label);
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
      const chosen = this.queryParams[queryKey]// this.getChosenItemsArr()
      // 检索项 + 已选项的并集
      const result = [...chosen.filter(item => !arr.includes(item)), ...arr]
      if (arr.length > maxLength) {
        this.selectOptions[optionKey].top100.push(...result)
      } else {
        this.selectOptions[optionKey].top100.push(...result)

      }
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
    showSearch(prop) {

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
</style>
