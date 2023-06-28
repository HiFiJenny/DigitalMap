<template>
  <div class="ksh">

    <div id="load" v-show="fullLoading">
      <div class="load_img"><!-- 加载动画 -->
        <img class="jzxz1" src="../../assets/images/home/jzxz1.png">
        <img class="jzxz2" src="../../assets/images/home/jzxz2.png">
      </div>
    </div>
    <div class="head_top">

      <div class="left_top">
        <div @click="routePage('/dataList/index')" class="top_home_btn">
          <img style="width: 18px;height: 18px" src="../../assets/images/home/home.png"/>
          <div style="color: #FFF;font-size: 12px;padding-left: 2px">首页</div>
        </div>
        <dv-decoration-8 :color="['#00a2e8','#00a2e8']" style="width: 100%"/>
      </div>

      <div class="center_top">
        <div class="head_title">典型民机型号计量溯源保障电子地图</div>
        <dv-decoration-5 :color="['#00a2e8','#00a2e8']" class="" style="height: 20px"/>
      </div>
      <dv-decoration-8 :color="['#00a2e8','#00a2e8']" class="right_top" :reverse="true"/>

    </div>
    <div class="visual">
      <div class="visual_left">
        <div>
          <div class="search_eq">
            <div>
              <el-tooltip content="默认选择全部机型，可输入机型进行搜索匹配">
                <div> 机型：</div>
              </el-tooltip>
            </div>
            <el-select style=" " filterable
                       @change="equipmentChange"
                       collapse-tags clearable
                       v-model="selectModels" placeholder="全部">
              <el-option
                v-for="item in modelList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
              <el-option v-if="modelListTop100.length >= 100 && modelList.length > 100" value="tip" disabled>
                <span class="gray">--只展示前100条数据，更多数据请输入查询--</span>
              </el-option>
            </el-select>
            <div style="margin-left: 8px">
              <el-tooltip content="默认选择全部架次，可输入架次进行搜索匹配">
                <div> 架次：</div>
              </el-tooltip>
            </div>
            <el-select style="" filterable
                       @change="equipmentChange"
                       collapse-tags clearable
                       v-model="selectPlaneNos" placeholder="全部">
              <el-option
                v-for="item in planeNoList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
              <el-option v-if="planeNoListTop100.length >= 100 && planeNoList.length > 100" value="tip" disabled>
                <span class="gray">--只展示前100条数据，更多数据请输入查询--</span>
              </el-option>
            </el-select>
            <!--            <el-form label-width="80px" style="display: flex">

                          <el-form-item label="机型" style="flex:1 ">

                          </el-form-item>
                          <el-form-item label="架次" style="flex:1 ">

                          </el-form-item>
                        </el-form>-->
            <!--            <div>
                          <el-tooltip content="默认选择全部设备，可输入设备名称进行搜索匹配，支持单选或者多选">
                            <div> 设备：</div>
                          </el-tooltip>
                        </div>
                        <el-select style="flex-basis: 65%" filterable multiple
                                   @change="equipmentChange"
                                   clearable
                                   collapse-tags
                                   :filter-method="filterCheckPerOptions"
                                   v-model="equipmentNames" placeholder="全部">
                          <el-option
                            v-for="item in equipmentListTop100"
                            :key="item"
                            :label="item"
                            :value="item">
                          </el-option>
                          <el-option v-if="equipmentListTop100.length >= 100 && equipmentList.length > 100" value="tip" disabled>
                            <span class="gray">&#45;&#45;只展示前100条数据，更多数据请输入查询&#45;&#45;</span>
                          </el-option>
                        </el-select>
                        <el-button @click="openMoreDialog" type="text" style="padding-left:6px;height: 32px;flex-basis: 10%">
                          更多条件<i class="el-icon-arrow-down"></i></el-button>-->
          </div>

          <div class="afo_ctn">
            <div class="ao_fo" @click="dataFilter({source:'ao',  type:'afo',})">
              <div class="afo_title">AO</div>
              <div class="afo_group">
                <div class="afo_num">{{ afo.aoCount }}</div>
                <div class="afo_unit">项</div>
              </div>
            </div>
            <div class="ao_fo" @click="dataFilter({source:'fo', type:'afo'})">
              <div class="afo_title">FO</div>
              <div class="afo_group">
                <div class="afo_num">{{ afo.foCount }}</div>
                <div class="afo_unit">项</div>
              </div>
            </div>
          </div>
        </div>
        <div style="flex: 1">
          <div class="visual_box_half">
            <div class="visual_title">
              <span>
                 <el-tooltip>
                     <div slot="content">中国商飞是否具有试验能力”中是的占比和项数<br/>
                       中国商飞测试送外检定”中否的占比以及送外检定'是'的次数</div>
                      <span> 测试参数能力情况</span>
                    </el-tooltip>
                </span>
              <img src="../../assets/images/home/ksh33.png">
            </div>
            <div class="visual_chart">
              <div style="width: 100%;height: 50%;display: flex">
                <div id="chartOne" class="six_percent"
                     @click="dataFilter({'externalCalibration':'是', type:'afo'})"></div>
                <div class="four_percent" @click="dataFilter({'experimentCapacity':'是', type:'afo'})">
                  <div class="chart_name">
                    <el-tooltip content="中国商飞是否具有试验能力”中是的占比和项数">
                      <div> 具备项数</div>
                    </el-tooltip>
                  </div>
                  <div><span class="chart_num">{{ capacity.count }}</span> <span class="chart_unit">项</span></div>
                </div>
              </div>

              <div style="width: 100%;height: 50%;display: flex">
                <div id="chartTwo" class="six_percent"
                     @click="dataFilter({'externalCalibration':'否', type:'afo'})"></div>
                <div class="four_percent" @click="dataFilter({'externalCalibration':'是', type:'afo'})">
                  <div class="chart_name">
                    <el-tooltip content="中国商飞测试送外检定”中否的占比以及送外检定'是'的次数">
                      <div> 送外检定</div>
                    </el-tooltip>
                  </div>
                  <div><span class="chart_num">{{ outside.count }}</span> <span class="chart_unit">次</span></div>
                </div>
              </div>

            </div>
          </div>
          <div class="visual_box_half">
            <div class="visual_title">
              <span>
                   <el-tooltip content="“试验层级”中各项的数量和占比">
                      <span> 各层级试验情况</span>
                    </el-tooltip>
                </span>
              <img src="../../assets/images/home/ksh33.png">

            </div>
            <div class="visual_chart" id="chartFour">

            </div>
          </div>
        </div>
      </div>
      <div class="visual_con">
        <div class="visual_conTop">
          <div class="visual_title">
            <span>
                  <el-tooltip content="“试验/测试所属飞机研制阶段”列G1-G11每个阶段分别所占项数。">
                      <span> 研制过程测量环节和参数分布</span>
                    </el-tooltip>
              </span>
            <img src="../../assets/images/home/ksh33.png">
          </div>
          <div class="visual_chart">
            <div style="height: 100%;display: flex;flex-direction: column">
              <div style="height: 40px;display: flex;padding: 10px 10% 0 5%;">
                <div v-for="index in 10" :key="index" style="flex: 1;cursor: pointer"
                     @click="dataFilter({stages:['G'+index], type:'afo'})"
                     class="phase">
                  <div class="phase_item" style=""></div>
                  <div class="phase_name">G{{ index }}</div>
                </div>
                <div class="phase" @click="dataFilter({stages:['G11'], type:'afo'})">
                  <div class="phase_item" style=""></div>
                  <div class="phase_name">G{{ '11' }}</div>
                </div>
              </div>
              <div style="flex: 1;height: 100%" id="chartThree">

              </div>
            </div>
          </div>
        </div>
        <div class="visual_conCenter">
          <div class="visual_title">
            <span>
               <el-tooltip content="“飞机各区域部件工序、设备、关重件数量。">
                      <span> 各部件工序设备情况</span>
               </el-tooltip>
              </span>
            <img src="../../assets/images/home/ksh33.png">
          </div>
          <div class="visual_chart">
            <div id="plane_container">
              <div class="part " v-for="(item,index) in  planePart"
                   :class="'part'+(index+1)"
                   :style="{transform:'scale('+fontScale+')'}">
                <el-popover
                  @show="showPopOver(item)"
                  placement="top"
                  popper-class="black_popover"
                  width="200"
                  trigger="hover"
                >
                  <div v-loading="partCountShow">
                    <div class="part_poppover_title" style="">
                      <div>工序</div>
                      <div>设备</div>
                      <div>关重件</div>
                    </div>
                    <div class="part_poppover_data">
                      <div>{{ partCount.workflowCount }}</div>
                      <div>{{ partCount.equipmentCount }}</div>
                      <div>{{ partCount.importantCount }}</div>
                    </div>

                    <div @click="dataFilter({area:item,  type:'part'})" class="more">更多<i
                      class="el-icon-d-arrow-right"></i></div>
                  </div>
                  <span slot="reference">{{ item }}</span>
                </el-popover>
              </div>
              <img id="chart_plane" src="../../assets/images/home/plane.png" alt="">

            </div>

          </div>
        </div>
        <div class="visual_conBot">
          <div class="visual_title">
            <span>
               <el-tooltip content="“试验分类”中各项的数量">
                      <span> 各类别试验的测量情况</span>
               </el-tooltip>
            </span>
            <img src="../../assets/images/home/ksh33.png">
          </div>
          <div class="visual_chart" id="chartSix">
          </div>
        </div>

      </div>
      <div class="visual_right">
        <div class="visual_box">
          <div class="visual_title">
            <span>
                <el-tooltip content="“试验/测试数据所属飞机系统”中各系统的数量和占比。">
                      <span> 各系统测量分布</span>
               </el-tooltip>
            </span>
            <img src="../../assets/images/home/ksh33.png">
          </div>
          <div class="visual_chart" id="chartFive" style="height: 100%">
          </div>
        </div>
        <div class="visual_box ">
          <div class="visual_title">
            <span>
                <el-tooltip content="各型号测量过程数量、参数数量的所占行数。">
                      <span> 各型号测量分布</span>
               </el-tooltip>
            </span>
            <img src="../../assets/images/home/ksh33.png">
          </div>
          <div class="visual_chart" style="display: flex;align-items: center;justify-content: center;">
            <div class="flexCenter" id="chartNine">

            </div>

          </div>
        </div>
        <div class="visual_box">
          <div class="visual_title">
            <span>
             <el-tooltip content="各型号数据中‘是否关键’是与全部的比值">
                      <span> 各型号关键参数占比</span>
               </el-tooltip>
            </span>
            <img src="../../assets/images/home/ksh33.png">
          </div>
          <div class="visual_chart" style="display: flex">
            <div style="height: 100%;flex: 1" id="chartSeven"></div>
            <div style="height: 100%;flex:1;" id="chartEight"></div>
          </div>
        </div>
      </div>
      <div class="clear"></div>
    </div>

    <el-dialog
      title="提示"
      :modal="false"
      :visible.sync="moreDialogShow"
      width="50%"
    >
      <el-form label-width="80px">
        <el-form-item label="设备">
          <el-select style="width: 75%" filterable multiple
                     collapse-tags
                     :filter-method="filterCheckPerOptions"
                     v-model="equipmentNames" placeholder="全部">
            <el-option
              v-for="item in equipmentListTop100"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
            <el-option v-if="equipmentListTop100.length >= 100 && equipmentList.length > 100" value="tip" disabled>
              <span class="gray">--只展示前100条数据，更多数据请输入查询--</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="机型">
          <el-select style="width: 75%" filterable multiple
                     collapse-tags clearable
                     :filter-method="filterCheckModelOptions"
                     v-model="selectModels" placeholder="全部">
            <el-option
              v-for="item in modelListTop100"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
            <el-option v-if="modelListTop100.length >= 100 && modelList.length > 100" value="tip" disabled>
              <span class="gray">--只展示前100条数据，更多数据请输入查询--</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="架次">
          <el-select style="width: 75%" filterable multiple
                     collapse-tags clearable
                     :filter-method="filterCheckPlaneNoOptions"
                     v-model="selectPlaneNos" placeholder="全部">
            <el-option
              v-for="item in planeNoListTop100"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
            <el-option v-if="planeNoListTop100.length >= 100 && planeNoList.length > 100" value="tip" disabled>
              <span class="gray">--只展示前100条数据，更多数据请输入查询--</span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="moreDialogShow = false">取 消</el-button>
        <el-button type="primary" @click="queryMore">确 定</el-button>
      </span>
    </el-dialog>
    <!--    <el-popover
          placement="bottom"
          title="标题"
          width="200"
          trigger="manual"
          content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。"
          v-model="planePopoverShow">
          <el-button   @click="planePopoverShow = !planePopoverShow">手动激活</el-button>
        </el-popover>-->
  </div>
</template>

<script>
import './echarts-liquidfill.min'
import *  as echarts from "echarts";
import vueSeamlessScroll from 'vue-seamless-scroll'
import moment from "moment";
import {
  statisticAllequipment,
  statisticAllModel,
  statisticAllPlaneNo,
  statisticConfigModel,
  statisticConfigPlaneNo,

  statisticAreaCount,
  statisticEquipmentApply,
  statisticExperimentCapacity,
  statisticDevelopStage, statisticExperimentLevel,
  statisticExperimentClassify,
  statisticAircraftSystem,
  statisticExperimentParam,
  statisticImportant
} from "@/api/biz/statistic";
import {debounce} from "@/utils";

let echartsObjList = []
export default {
  name: 'index',
  components: {vueSeamlessScroll},
  data() {

    return {
      fontScale: 1,
      fullLoading: true,
      planePopoverShow: false,
      planePart: ['机头', '前机身', '起落架', '中机身', '短仓/吊挂', '机翼', '中后机身', '后机身', '垂尾', '平尾'],
      equipmentNames: [],
      equipmentList: [],
      equipmentListTop100: [],
      moreDialogShow: false,
      //机型
      selectModels: null,
      modelList: [],
      modelListTop100: [],
      //架次
      selectPlaneNos: null,
      planeNoList: [],
      planeNoListTop100: [],
      capacity: {
        count: 0,
        ratio: 0
      },
      outside: {
        count: 0,
        ratio: 0
      },
      afo: {
        aoCount: 0,
        foCount: 0,
      },
      partCountShow: true,
      partCount: {
        "workflowCount": 0,
        "equipmentCount": 0,
        "importantCount": 0
      },
      resizeHandler: null,
      initHandler: null,

    }
  },
  computed: {},
  destroyed() {
    echartsObjList = []
  },
  created() {
    this.init()

/*    statisticAllequipment().then(res => {
      this.equipmentList = res.data
      this.equipmentListTop100 = this.equipmentList.slice(0, 100)
      console.log(res)

    })*/
    statisticConfigModel().then(res => {
      this.modelList = res.data
      this.modelListTop100 = this.modelList.slice(0, 100)
      console.log(res)

    })
    statisticConfigPlaneNo().then(res => {
      this.planeNoList = res.data
      this.planeNoListTop100 = this.planeNoList.slice(0, 100)
      console.log(res)

    })
    statisticEquipmentApply({}).then(res => {
      this.afo = res.data
    })

    setTimeout(() => {
      this.fullLoading = false
    }, 500)

  },
  mounted: function () {
    window.addEventListener("resize", this.resizeEcharts);
    this.resizePlane()

  },
  beforeDestroy() {
    window.removeEventListener("resize", this.resizeEcharts);
    echartsObjList = []
  },
  methods: {
    init() {

      echartsObjList = []
      const timeFuncs = [this.drawOne, this.drawTwo, this.drawThree, this.drawFour, this.drawFive, this.drawSix, this.drawSeven, this.drawNine]
      for (let timeFunc of timeFuncs) {
        timeFunc()
      }
    },
    debounceInit() {
      if (!this.initHandler) {
        this.initHandler = debounce(() => {
          this.init()
        }, 500)
      }
      return this.initHandler
    },
    resizeEcharts() {
      const self = this
      this.resizePlane()
      setTimeout(() => {
        for (let k = 0; k < echartsObjList.length; k++) {
          echartsObjList[k].resize()
        }
      }, 100)
    },
    resizePlane() {
      let visual_chart = document.getElementById("plane_container").parentElement;
      let height = visual_chart.offsetHeight - 40
      let width = visual_chart.offsetWidth - 40
      console.log(height, width)
      let pic_height = 143
      let pic_width = 310

      let plane_container = document.getElementById("plane_container")
      let s = this.scalingImage(pic_width, pic_height, width, height)
      let scale = s * 0.9

      plane_container.style.transform = `scale(${scale})`
      // plane_container.style.height=s.height+'px'
      // plane_container.style.width=s.width+'px'
      this.fontScale = scale > 1 ? (1 / scale) : 1
      //计算部件位置

    },
    scalingImage(imgWidth, imgHeight, containerWidth, containerHeight) {
      var containerRatio = containerWidth / containerHeight;
      var imgRatio = imgWidth / imgHeight;
      console.log('imgRatio', imgRatio, containerRatio)

      if (imgRatio > containerRatio) {
        imgWidth = containerWidth;
        imgHeight = containerWidth / imgRatio;
      } else if (imgRatio < containerRatio) {
        imgHeight = containerHeight;
        imgWidth = containerHeight * imgRatio;
      } else {
        imgWidth = containerWidth;
        imgHeight = containerHeight;
      }
      return imgWidth / 310
      // return { width: imgWidth, height: imgHeight };
    },
    showPopOver(part) {
      console.log(part)
      this.partCountShow = true;
      statisticAreaCount({
        area: part,
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {
        this.partCountShow = false;
        this.partCount = res.data
      })
    },
    dataFilter(condition) {
      let route = this.$router.resolve({
        path: '/dataFilter/area',
        query: {
          ...condition,
        }
      })
      localStorage.setItem("condition", JSON.stringify({
        ...condition,
        'equipmentNames': this.equipmentNames,
        'models': this.selectModels?[this.selectModels]:[],
        'planeNos': this.selectPlaneNos?[this.selectPlaneNos]:[],
      }))
      window.open(route.href, '_blank')

    },
    routePage(path, params) {
      this.$router.push(path)

    },
    equipmentChange() {
      statisticEquipmentApply({
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {
        this.afo = res.data
      })
      let waitdebounceInit = this.debounceInit()
      waitdebounceInit()
    },
    filterCheckPerOptions(query = '') {
      debugger
      let maxLength = 100
      // query是输入框中的检索条件
      var arr = this.equipmentList.filter(item => {
        return item && item.includes(query)
      })
      // 根据检索条件筛选出来的选项，只取前100条
      if (arr.length > maxLength) {
        arr = arr.slice(0, maxLength)
      }
      // 清空之前的选项
      this.equipmentListTop100.splice(0, this.equipmentListTop100.length)
      // chosen表示已被选择的选项，添加这一部分主要是为了回显，避免选择框中直接出现用户id
      const chosen = this.equipmentNames// this.getChosenItemsArr()
      // 检索项 + 已选项的并集
      const result = [...chosen.filter(item => !arr.includes(item)), ...arr]
      if (arr.length > maxLength) {
        this.equipmentListTop100.push(...result)
      } else {
        this.equipmentListTop100.push(...result)
      }
    },
    openMoreDialog() {
      this.moreDialogShow = true;
    },
    queryMore() {
      statisticEquipmentApply({
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {
        this.afo = res.data
        this.moreDialogShow = false;
      })
      let waitdebounceInit = this.debounceInit()
      waitdebounceInit()
    },

    filterCheckModelOptions(query = '') {

      let maxLength = 100
      // query是输入框中的检索条件
      var arr = this.modelList.filter(item => {
        return item && item.includes(query)
      })
      // 根据检索条件筛选出来的选项，只取前100条
      if (arr.length > maxLength) {
        arr = arr.slice(0, maxLength)
      }
      // 清空之前的选项
      this.modelListTop100.splice(0, this.modelListTop100.length)
      // chosen表示已被选择的选项，添加这一部分主要是为了回显，避免选择框中直接出现用户id
      const chosen = this.selectModels// this.getChosenItemsArr()
      // 检索项 + 已选项的并集
      const result = chosen ? [chosen, ...arr] : arr
      if (arr.length > maxLength) {
        this.modelListTop100.push(...result)
      } else {
        this.modelListTop100.push(...result)
      }
    },
    filterCheckPlaneNoOptions(query = '') {

      let maxLength = 100
      // query是输入框中的检索条件
      var arr = this.planeNoList.filter(item => {
        return item && item.includes(query)
      })
      // 根据检索条件筛选出来的选项，只取前100条
      if (arr.length > maxLength) {
        arr = arr.slice(0, maxLength)
      }
      // 清空之前的选项
      this.planeNoListTop100.splice(0, this.planeNoListTop100.length)
      // chosen表示已被选择的选项，添加这一部分主要是为了回显，避免选择框中直接出现用户id
      const chosen = this.selectPlaneNos// this.getChosenItemsArr()
      // 检索项 + 已选项的并集
      console.log( arr.indexOf(chosen))
      const result =arr.indexOf(chosen)<0 && chosen? [chosen, ...arr] : arr
      if (arr.length > maxLength) {
        this.planeNoListTop100.push(...result)
      } else {
        this.planeNoListTop100.push(...result)
      }
    },
    getChosenItemsArr() {
      // 获取已被选中的人员
      const items = []
      for (let i = 0; i < this.equipmentList.length; i++) {
        if (this.equipmentNames.indexOf(this.equipmentList[i].id) >= 0 &&
          items.indexOf(this.equipmentList[i]) < 0) {
          items.push(this.equipmentList[i])
        }
      }
      return items
    },

    drawOne() {
      console.log('drawOne start', new Date())
      statisticExperimentCapacity({
        type: 1,
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {
        let data = res.data[0]
        this.capacity = data ? data : this.capacity
        let ratio = parseFloat(this.capacity.ratio) * 100

        let colorList = ['#285bea', '#60e4f6']
        const colorSet = [
          [0.3, '#3F4F5DCC'],
          [1, '#3F4F5DCC'],
        ];
        let shujv = [];
        shujv.push({
          name: '具备能力占比',
          value: ratio,
          itemStyle: {
            color: { //图形渐变颜色方法，四个数字分别代表，右，下，左，上，offset表示0%到100%
              type: 'linear',
              x: 0,
              y: 0,
              x2: 1, //从左到右 0-1
              y2: 0,
              colorStops: [{
                offset: 0.3,
                color: colorList[0]
              }, {
                offset: 1,
                color: colorList[1]
              }],
            },
          },
        })

        function getColor2(x, y, r) {//圆心颜色变色
          return {
            type: "radial",
            x,
            y,
            r,
            colorStops: [
              {offset: 1, color: colorList[0]},
              {offset: 0, color: colorList[1]}
            ]
          }
        }

        //绘制仪表盘
        let option = {
          backgroundColor: "rgba(0,0,0,0)",
          title: [
            {
              text: "● 中国商飞测试参数具备能力情况",
              top: 0,
              left: 0,
              textStyle: {
                fontWeight: "normal",
                fontSize: 14,
                color: "#3dbee7",
              },
            },
          ],
          series: [{
            type: 'gauge',
            radius: '95%',
            center: ['50%', '65%'],
            max: 100,
            min: 0,
            z: 9999,
            startAngle: 180,
            endAngle: 0,
            pointer: {//仪表盘指针
              show: false,
              // length: '18%',
              width: 25,
              icon: 'circle',
              offsetCenter: [0, '-67%'],
              itemStyle: {
                color: colorList[1]
              }
            },
            progress: {//仪表盘进度
              show: true,
              roundCap: true,
              width: 10
            },
            splitNumber: 9,
            detail: {
              formatter: function (value) {
                var num = value;
                return '{bule|' + num + '}{bule|%}';
              },
              rich: {
                bule: {
                  fontSize: 16,
                  fontFamily: 'siyaun',
                  color: '#fff8ff',
                  fontWeight: '600',
                },
              },
              offsetCenter: ['0%', '-20%'],
            },

            data: shujv,
            title: {
              show: false,
              color: '#01f0ff',
            },
            axisLine: {
              show: true,
              roundCap: true,
              lineStyle: {
                color: colorSet,
                width: 10
              },
            },
            axisTick: {//仪表盘刻度
              show: true,
              splitNumber: 10,
              length: 2,
              lineStyle: {
                color: colorList[1],
                width: 1,
                type: 'solid',
              },
              distance: 1,
            },
            splitLine: {//仪表盘分割线
              show: true,
              length: 2,
              distance: 1,
              lineStyle: {
                color: colorList[1],
                width: 2,
                type: 'solid',
              },
            },
            axisLabel: {
              show: false,
            },
          },

            {
              name: 'Nightingale Chart',
              type: 'pie',
              radius: '60%',
              center: ['50%', '66%'],
              roseType: 'radius',
              silent: true,
              startAngle: 180,
              legendHoverLink: false,
              itemStyle: {
                borderRadius: 0,
              },
              label: {
                show: false,
              },
              emphasis: {
                label: {
                  show: false,
                },
              },
              color: [getColor2(0.4, 0, 2), '#FFFFFF00'],
              data: [
                {value: 10, name: 'r'},
                {value: 10, name: 'r0'},
              ],
            },
          ],
        }
        this.$nextTick(() => {
          let chartOne = echarts.init(document.getElementById('chartOne'));
          chartOne.setOption(option);
          echartsObjList.push(chartOne)
          console.log('drawOne end', new Date())

        })
      })

    },
    drawTwo() {
      console.log('drawTwo start', new Date())

      statisticExperimentCapacity({
        type: 2,
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {
        let data = res.data[0]
        this.outside = data ? data : this.outside

        const rate = parseFloat(this.outside.ratio) * 100
        let option = {
          backgroundColor: 'rgba(0,0,0,0)', //设置背景颜色
          title: [
            {
              text: "● 中国商飞测试参数量传能力情况",
              top: 0,
              left: 0,
              textStyle: {
                fontWeight: "normal",
                fontSize: 14,
                color: "#3dbee7",
              },
            },
            {
              text: "{a|" + rate + "%}",
              show: true,
              left: 'center',
              top: 'middle',
              textStyle: {
                rich: {
                  a: {
                    fontSize: 30,
                    color: "#FFFFFF",
                    fontWeight: "bold",
                  },
                },
              },
            },

          ],
          polar: {
            center: ["50%", "50%"],
            radius: ["60%", "75%"],
          },
          angleAxis: {
            max: 100,
            show: false,
          },
          radiusAxis: {
            type: "category",
            show: true,
            axisLabel: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
          },
          series: [
            {
              data: [rate],
              name: "",
              type: "bar",
              roundCap: true,
              showBackground: true,
              backgroundStyle: {
                color: "rgba(19, 84, 146, .4)",
              },
              coordinateSystem: "polar",
              itemStyle: {
                normal: {
                  color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                    {
                      offset: 0,
                      color: "#005DCF",
                    },
                    {
                      offset: 1,
                      color: "#00CCFF",
                    },
                  ]),
                },
              },
            },
          ],
        }

        this.$nextTick(() => {
          let chartTwo = echarts.init(document.getElementById('chartTwo'));
          chartTwo.setOption(option);
          echartsObjList.push(chartTwo)
          console.log('drawTwo end', new Date())

        })
      })

    },
    drawThree() {
      console.log('drawThree start', new Date())

      statisticDevelopStage({
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {
        console.log('drawThree res end', new Date())

        console.log(res)
        let xdata = ['G1', 'G2', 'G3', 'G4', 'G5', 'G6', 'G7', 'G8', 'G9', 'G10', 'G11']
        let data = res.data
        let seriesData = xdata.map(item => {
          let stage = data.find(value => value.name === item)
          return {
            name: item,
            value: stage ? stage.count : 0,
          }
        })
        console.log(seriesData)
        let option = {
          color: ['#4db', '#01c2f9'],
          backgroundColor: "rgba(0,0,0,0)",

          grid: {
            top: 10,
            left: '5%',
            right: '10%',
            bottom: 20
          },
          xAxis: [
            {
              type: 'category',
              data: xdata,
              axisLabel: {
                show: false,
                color: '#01f0ff',
              },
              axisTick: {
                show: false
              },

            }
          ],
          yAxis: [
            {
              type: 'value',
              // name: 'Precipitation',
              // interval: 50,
              axisLabel: {
                show: true,
                color: '#01f0ff',
                formatter: function (value, index) {
                  if (value >= 100000000) {
                    return value / 100000000 + "亿";
                  } else if (value >= 10000000) {
                    return value / 10000000 + "千万";
                  } else if (value >= 1000000) {
                    return value / 1000000 + "百万";
                  } else if (value >= 100000) {
                    return value / 100000 + "十万";
                  } else if (value >= 10000) {
                    return value / 10000 + "万";
                  } else if (value >= 1000) {
                    return value / 1000 + "千";
                  } else {
                    return value;
                  }
                }
              },
              splitLine: {
                lineStyle: {
                  color: 'rgba(132,129,129,0.1)'
                }
              }
            },

          ],
          series: [
            {
              name: 'bar',
              type: 'bar',
              tooltip: {
                valueFormatter: function (value) {
                  return value;
                }
              },
              barWidth: '25%',
              itemStyle: {
                normal: {
                  barBorderRadius: 15,
                  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: '#248ff7'
                  }, {
                    offset: 1,
                    color: '#6851f1'
                  }]),
                },
              },
              label: {
                show: true,
                formatter: '{b}'
              },
              data: seriesData
            },
            /*
                        {
                          name: 'line',
                          type: 'line',
                          tooltip: {
                            valueFormatter: function (value) {
                              return value + ' °C';
                            }
                          },
                          data: seriesData
                        }*/
          ]
        };

        this.$nextTick(() => {
          let chartThree = echarts.init(document.getElementById('chartThree'));
          chartThree.setOption(option);
          echartsObjList.push(chartThree)
          console.log('drawThree end', new Date())

        })

      })
    },
    drawFour() {
      statisticExperimentLevel({
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {

        let data = res.data.length >= 30 ? res.data.slice(0, 30) : res.data
        const chartData = data.map(item => {
          return {
            value: item.count,
            name: item.name,
            ratio: item.ratio
          }
        })
        const colorList = ['#88D9FF', '#0092FF',
          '#81EDD2', '#B0FA93',
          '#63F2FF', '#9999FE',
          '#10EBE3', '#10A9EB', '#EB9C10',
          '#2E10EB', '#9B10EB',];
        const sum = chartData.reduce((per, cur) => per + cur.value, 0);
        const gap = (1 * sum) / 100;
        const pieData1 = [];
        const gapData = {
          name: "",
          value: gap,
          itemStyle: {
            color: "transparent",
          },
        };

        //图标位置显示
        let init_top = 2;
        let lefts = [];
        let tops = [];
        let legendData = [];
        let total = 0;
        chartData.forEach((item) => {
          lefts.push('1%')
          tops.push(init_top + '%')
          init_top += 6
          total += item.value;
        });

        for (let i = 0; i < chartData.length; i++) {
          // 第一圈数据
          pieData1.push({
            ...chartData[i],
            itemStyle: {
              borderRadius: 10,
            },
          });
          pieData1.push(gapData);

          //  分散图例
          let bfb = chartData[i].ratio + "%";
          legendData.push({
            show: true,
            icon: "circle", //'circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow', 'none'
            left: lefts[i],
            top: tops[i],
            itemStyle: {
              color: colorList[i],
            },
            formatter:
              `{aa| ` + chartData[i].name + ` }` + `{bb| ` + bfb + `}`, // 也可以是个函数return
            x: "left",
            textStyle: {
              // color: "#BAFF7F",
              rich: {
                aa: {
                  color: "#ffffff",
                },
                bb: {
                  color: colorList[i],
                },
              },
            },
            data: [chartData[i].name],
          });
        }

        let option = {
          backgroundColor: 'rgba(0, 0, 0,0)',
          // legend: legendData,
          grid: {
            top: 30,
            right: 20,
            bottom: 10,
            left: 10,
          },
          color: colorList,
          series: [
            {
              name: '',
              type: 'pie',
              roundCap: true,
              radius: ['66%', '70%'],
              center: ['50%', '45%'],
              labelLine: {
                normal: {
                  length: 10,
                  length2: 14,
                }
              },
              label: {
                show: true,
                position: 'outer',
                alignTo: 'labelLine',

                distanceToLabelLine: 0,
                borderRadius: 2.5,
                padding: [2.5, -2.5, 2.5, -2.5],
                formatter: function (params) {
                  if (params.name !== '') {
                    return `{a|${params.name}：}{b|${params.value}}`;
                  } else {
                    return '';
                  }
                },
                rich: {
                  a: {
                    padding: [0, 0, 0, 10],
                    color: '#fff'
                  },
                  b: {
                    padding: [0, 10, 0, 0],
                    color: '#fff'
                  },
                }
              },

              data: pieData1
            },
            {
              type: 'gauge',
              zlevel: 2,
              splitNumber: 90,
              radius: '60%',
              center: ['50%', '45%'],
              startAngle: 90,
              endAngle: -269.9999,
              axisLine: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLabel: {
                show: false,
              },
              splitLine: {
                show: true,
                length: 7,
                lineStyle: {
                  width: 4,
                  color: 'rgb(33,85,130)',
                },
              },
              pointer: {
                show: 0,
              },
              detail: {
                show: 0,
              },
            },
            {
              type: 'pie',
              center: ['50%', '45%'],
              radius: [0, '45.6%'],
              label: {
                show: false
              },
              labelLine: {
                show: false
              },
              itemStyle: {
                color: 'rgba(75, 126, 203,.1)'
              },
              silent: true,
              data: [
                {
                  value: 100,
                  name: ''
                }
              ]
            }
          ],
        };

        this.$nextTick(() => {
          let chartFour = echarts.init(document.getElementById('chartFour'));
          chartFour.setOption(option);
          echartsObjList.push(chartFour)
        })
      })

    },
    drawFive() {
      statisticAircraftSystem({
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {
        let data = res.data.length >= 30 ? res.data.slice(0, 30) : res.data
        const chartData = data.map(item => {
          return {
            value: item.count,
            name: item.name,
            ratio: item.ratio
          }
        })

        let option = {
          color: [
            '#1EE7E7', '#BEE5FB',
            '#00D68A', '#1890FF',
            '#564AF1', '#56ffd7',
            '#6DA7FF', '#2B64FF',
            '#68FF83', '#8792FF',
            '#F6FD6A', '#B0FA93',
            '#AEC1E0', '#84B4FE',
            '#10EBE3', '#10A9EB', '#EB9C10',
            '#2E10EB', '#9B10EB',
          ],
          legend: {
            orient: 'vertical',
            left: 'right',
            icon: "circle",
            textStyle: {
              // color: "#BAFF7F",
              rich: {
                aa: {
                  color: "#ffffff",
                },
                bb: {
                  color: '#ffffff',
                },
              },
            },
            formatter: ' {aa|{name} }'
          },
          series: [
            {
              name: '',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              itemStyle: {
                // borderRadius: 10,
                /* borderColor: '#fff',
                 borderWidth: 2*/
              },
              label: {
                show: true,
                position: 'inside',
                formatter: '{d}%'
              },
              emphasis: {

                label: {
                  show: true,
                  fontSize: 40,
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: true
              },
              data: chartData
            }
          ]
        };

        this.$nextTick(() => {
          let chartFive = echarts.init(document.getElementById('chartFive'));
          chartFive.setOption(option);
          echartsObjList.push(chartFive)
        })
      })

    },
    drawSix() {
      console.log('drawSix start', new Date())

      statisticExperimentClassify({
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {
        console.log('drawSix res end', new Date())

        let data = res.data.length >= 30 ? res.data.slice(0, 30) : res.data
        const chartData = data.map(item => {
          return item.count
        })
        let xdata = data.map(item => {
          return item.name
        })
        let option = {
          backgroundColor: 'rgba(0,0,0,0)',
          color: ['#30f3e3', '#46f', '#9cf'],
          grid: {
            bottom: '21%',
            top: '10%',
          },
          xAxis: {
            type: 'category',
            axisTick: {
              show: false
            },
            axisLabel: {
              // show: false
              interval: 0, //控制X轴刻度全部显示
              rotate: 45, //倾斜角度
              textStyle: {
                fontSize: 10

              }


            },
            data: xdata
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              show: true,

            },
            splitLine: {
              show: false,
            }
          },
          series: [
            {
              data: chartData,
              type: 'bar',
              barWidth: '25%',
              itemStyle: {
                normal: {
                  barBorderRadius: 15,
                },
              },

            }
          ]
        };

        this.$nextTick(() => {
          let chartSix = echarts.init(document.getElementById('chartSix'));
          chartSix.setOption(option);
          echartsObjList.push(chartSix)
          console.log('drawSix end', new Date())

        })
      })

    },
    drawSeven() {
      statisticImportant({
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {
        let data = res.data
        let eles = ['chartSeven', 'chartEight']
        data.forEach((item, index) => {
          console.log('drawSeven start', new Date())

          this.drawWaterPolo(parseFloat(item.ratio), eles[index], item.name)
        })

      })
    },
    drawWaterPolo(val, element, title) {

      let value = val

      let option = {
        backgroundColor: 'rgba(0,0,0,0)',
        title: [
          {
            text: title,
            x: 'center',
            y: '70%',
            textStyle: {
              fontSize: 14,
              fontWeight: '100',
              color: '#5dc3ea',
              lineHeight: 16,
              textAlign: 'center',
            },
          }
        ],

        series: [
          {
            type: 'liquidFill',
            radius: '47%',
            center: ['50%', '45%'],
            color: [
              {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {
                    offset: 0,
                    color: '#446bf5',
                  },
                  {
                    offset: 1,
                    color: '#2ca3e2',
                  },
                ],
                globalCoord: false,
              },
            ],
            data: [value], // data个数代表波浪数
            backgroundStyle: {
              borderWidth: 1,
              color: 'RGBA(51, 66, 127, 0.7)',
            },
            label: {
              normal: {
                textStyle: {
                  fontSize: 20,
                  color: '#fff',
                },
              }
            },
            outline: {
              show: false,
              borderDistance: 10,
              itemStyle: {
                borderWidth: 2,
                borderColor: '#112165',
              },
            },
          },


        ],
      };
      this.$nextTick(() => {
        let chart = echarts.init(document.getElementById(element));
        chart.setOption(option);
        echartsObjList.push(chart)
        console.log('drawSeven end', element, new Date())

      })
    },
    drawNine() {
      console.log('drawNine satrt', new Date())

      let color = ['#10EBE3', '#10A9EB', '#EB9C10', '#9B10EB']
      statisticExperimentParam({
        'equipmentNames': this.equipmentNames,
        'model': this.selectModels,
        'planeNo': this.selectPlaneNos,
      }).then(res => {
        console.log('drawNine res end', new Date())

        let data = res.data
        let initX = 25;
        let initY = 25
        let series = []
        let title = []
        for (let i = 0; i < data.length; i++) {
          let item = data[i]
          series.push({
            name: item.name + "过程",
            type: 'pie',
            radius: ['35%', '40%'],
            center: [initX + '%', initY + '%'],
            startAngle: 225,
            color: [color[i * 2], "transparent"],
            labelLine: {
              normal: {
                show: false
              }
            },
            label: {
              normal: {
                position: 'center'
              }
            },
            data: [{
              value: 75,
              name: '过程数量',
              label: {
                normal: {
                  formatter: '过程数量\n' + item.processCount,
                  textStyle: {
                    color: '#fff',
                    fontSize: 16

                  }
                }
              }
            }, {
              value: 25,
              name: '%',
              label: {
                normal: {
                  formatter: '',
                  textStyle: {
                    color: '#007ac6',
                    fontSize: 30

                  }
                }
              }
            },
              {
                value: 0,
                name: '%',
                label: {
                  normal: {
                    formatter: '',
                    textStyle: {
                      color: '#fff',
                      fontSize: 16

                    }
                  }
                }
              }]
          })
          initX += 50;
          title.push({
            text: item.name,
            left: 0,
            top: initY + '%',
            textStyle: {
              color: '#fff',
              fontSize: 16

            }
          })

          series.push({
            name: item.name + "参数",
            type: 'pie',
            radius: ['35%', '40%'],
            center: [initX + '%', initY + '%'],
            startAngle: 225,
            color: [new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
              offset: 0,
              color: color[i * 2 + 1]
            }, {
              offset: 1,
              color: color[i * 2 + 1]
            }]), "transparent"],
            labelLine: {
              normal: {
                show: false
              }
            },
            label: {
              normal: {
                position: 'center'
              }
            },
            data: [{
              value: 75,
              name: '遥感解译信息',
              label: {
                normal: {
                  formatter: '参数数量\n' + item.paramCount,
                  textStyle: {
                    color: '#fff',
                    fontSize: 16

                  }
                }
              }
            }, {
              value: 25,
              name: '%',
              label: {
                normal: {
                  formatter: '',
                  textStyle: {
                    color: '#FFF',
                    fontSize: 30

                  }
                }
              }
            },
              {
                value: 0,
                name: '%',
                label: {
                  normal: {
                    formatter: '',
                    textStyle: {
                      color: '#fff',
                      fontSize: 16

                    }
                  }
                }
              }]
          })
          initX = 25
          initY += 50
        }


        let option = {
          backgroundColor: 'rgba(0,0,0,0)',
          title: title,
          series: series
        };
        this.$nextTick(() => {
          let chart = echarts.init(document.getElementById('chartNine'));
          chart.setOption(option);
          echartsObjList.push(chart)
          console.log('drawNine end', new Date())

        })
      })

    }
  }
}
</script>

<style lang="scss" scoped>
$LABEL_COLOR: #00a2e8;
a,
p,
h1,
h2,
h3,
h4,
h5,
h6,
body,
span,
label,
div {
  padding: 0;
  margin: 0;
}

ul {
  padding: 0;
  margin: 0;
}

a {
  text-decoration: none !important;
}

.clear {
  clear: both;
}

html,
body,
form {
  overflow-y: auto;
  height: 100%;
}

/* 滚动条样式 */
::-webkit-scrollbar {
  width: 1px;
  height: 4px;
}

::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(255, 255, 255, 0.9);
  border-radius: 10px;
}

::-webkit-scrollbar-thumb {
  border-radius: 5px;
  background: rgba(66, 66, 66, 1);
  -webkit-box-shadow: inset 0 0 6px rgba(227, 227, 227, 87.5);
}

::-webkit-scrollbar-thumb:window-inactive {
  background: rgba(227, 227, 227, 0.5);
}

@font-face {
  font-family: yjsz;
  src: url('../../assets/styles/fonts/yjsz.TTF');
  /* IE9+,可以是具体的实际链接 */
}

.hasTagsView {
  .ksh {
    height: calc(100vh - 84px) !important;
  }
}

.ksh {
  //height: calc(100vh - 50px);
  //height: 100%;
  padding: 15px 15px 15px 15px;
  background: linear-gradient(180deg, rgba(20, 20, 20, 1) 0%, rgba(20, 20, 20, 1) 0%, rgba(22, 25, 34, 1) 47%, rgba(20, 20, 20, 1) 100%, rgba(20, 20, 20, 1) 100%);
  background-size: cover;
  overflow: hidden;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1999;
  width: 100%;
  height: 100%;
}

/* 加载旋转动画 */
#load {
  width: 100%;
  height: 100%;
  position: absolute;
  background: url(../../assets/images/home/data08.png) no-repeat #061537;
  background-size: cover;
  top: 0;
  left: 0;
  z-index: 999
}

#load .load_img {
  position: absolute;
  left: calc(50% - 182px);
  top: calc(50% - 182px);
}

.load_img img {
  position: absolute;
  left: 0;
  top: 0;
}

.load_img .jzxz1 {
  animation: xz1 8s infinite linear;
}

@keyframes xz1 {
  from {
    transform: rotate(0deg);
  }

  50% {
    transform: rotate(180deg);
  }

  to {
    transform: rotate(360deg);
  }
}

.load_img .jzxz2 {
  animation: xz2 7s infinite linear;
}

@keyframes xz2 {
  from {
    transform: rotate(0deg);
  }

  50% {
    transform: rotate(-180deg);
  }

  to {
    transform: rotate(-360deg);
  }
}

.head_top {
  position: relative;
  text-align: center;
  display: flex;
  align-items: center;
  height: 50px;

  .top_home_btn {
    position: absolute;
    top: -5px;
    left: 48px;
    display: flex;
    align-items: center;
    cursor: pointer;
  }

  .head_title {
    font-weight: 650;
    font-style: normal;
    font-size: 20px;
    color: #1298ff;
  }

  .left_top, .right_top {
    width: calc(40%);
    height: 50px;
  }

  .center_top {
    width: calc(20%);
    height: 50px;
  }
}

.visual {
  height: calc(100% - 60px);
  display: flex;
}

.visual_box_all {
  height: 100%;
}

.visual_box_half {
  height: 50%;

}

.visual_box {
  height: 33.3%;
}

.visual_title {
  position: relative;
  height: 35px;
  margin: 5px 0;
}

.visual_title span {
  color: #fff;
  font-size: 16px;
  line-height: 35px;
}

.visual_title img {
  //width: 100%;
  position: absolute;
  left: 0;
  bottom: 0;
}

.visual_chart {
  height: calc(100% - 35px);
}

.chart_header {
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;


}

div.no_fix_width {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

div.fix_width {
  width: 30%; //95px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.seamless-warp {
  height: calc(100%);

  overflow: hidden;
}

.seamless-oil-warp {
  height: calc(100% - 30px);

  overflow: hidden;
}

.unit {
  font-size: 12px;
  color: #FEC171;
}

.seamless-item {
  padding: 10px 0;
  display: flex;
  justify-content: center;
  align-items: center;


  .segment-val {
    color: #409eff;
    font-size: 12px;
  }

  .val {
    color: #409eff;
    font-size: 18px;
  }


}

.visual_left {
  width: 30%;
  height: 100%;
  display: flex;
  flex-direction: column;
  color: $LABEL_COLOR;
  //float: left;
  .search_eq {
    display: flex;
    align-items: center;
    padding-top: 10px;
  }

  .afo_ctn {
    display: flex;
    justify-content: flex-start;
    padding: 16px 0%;
    padding-left: 10%;
  }

  .ao_fo {
    //width: 150px;
    display: flex;
    align-items: center;
    position: relative;
    cursor: pointer;

    .afo_group {
      display: flex;
      align-items: center;
      justify-content: center;
      padding-left: 20px;
      position: relative;
    }

    .afo_title {
      font-size: 16px;
      color: #FFFFFF;
    }

    .afo_num {
      font-size: 28px;
    }

    .afo_unit {
      color: #FFFFFF;
      font-size: 16px;
      padding-left: 6px;
    }

  }

  .ao_fo:not(:last-child) {
    padding-right: 40px;
  }

  .afo_group::before {
    content: '';
    position: absolute;
    right: 0;
    left: 0;
    bottom: 0;
    z-index: -1;
    height: 2px;
    margin: -1px;
    background: linear-gradient(90deg, rgba(255, 255, 255, 0) 0%, #00bbf2 20%, rgba(255, 255, 255, 0) 99%)
  }
}


.visual_con {
  width: 40%;
  height: 100%;
  //padding: 25px 20px 0 20px;
}

.visual_right {
  width: 30%;
  height: 100%;
}

.visual_con .visual_conTop {
  height: calc(100% / 10 * 3);
  //margin-bottom: 10px;
}

.visual_con .visual_conBot {
  height: calc(100% / 10 * 3);
  //margin-bottom: 10px;
}


.visual_con .visual_conCenter {
  height: calc(100% / 10 * 4);

  background: url(../../assets/images/home/ksh41.png) no-repeat;
  background-size: 100% 100%;
  position: relative;

  > img {
    position: absolute;
    width: 25px;
    height: 25px;
  }

  .visual_conBot_l {
    position: absolute;
    left: 0;
    top: 0;
  }

  .visual_conBot_2 {
    position: absolute;
    right: 0;
    top: 0;
  }

  .visual_conBot_3 {
    position: absolute;
    right: 0;
    bottom: 0;
  }

  .visual_conBot_4 {
    position: absolute;
    left: 0;
    bottom: 0;
  }

  .visual_chart {
    width: 100%;
    height: 100%;
    //position: absolute;
    display: flex;
    align-items: center;
    justify-content: center;

  }

  .visual_chart_text {
    color: #fff;
    position: absolute;
    top: 15px;
    left: 15px;
    z-index: 99;
  }

  .visual_chart_text h1 {
    font-size: 26px;
    margin-bottom: 6px;
  }

  .visual_chart_text h2 {
    font-size: 20px;
  }

}


.visual_chart {
  color: #FFFFFF;
  //overflow: auto;
}

#main3 {
  width: 100%;
  height: 100%;
}


.plane {
  font-size: 16px;
  color: #FEC171;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.sort-num {
  width: 20px;
  height: 20px;
  font-size: 14px;
  border-radius: 50%;
  border: 2px #01f0ff solid;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 5px;
}

::v-deep .dv-scroll-board .rows .row-item {
  transition: all ease-in 0.15s !important;
}

.stat_count {
  display: flex;
  align-items: center;
  justify-content: center;
  line-height: 85px;
  color: #20dbfd;
  text-shadow: 0 0 25px #00d8ff;
  font-size: 46px;
  font-family: 'yjsz';
  text-align: right;
}

img.img_sep_bar {
  background-image: url(../../assets/images/home/ksh33.png);
}

#plane_container {
  position: relative;
  width: 384px;;
  height: 179px;
  //border: red 1px solid;
}


#chart_plane {
  /*  width: 310px;;
    height: 143px;*/
  width: 100%;
  height: 100%;
}

.part {
  position: absolute;
  font-weight: 400;
  font-style: normal;
  color: #1DECED;
  font-size: 16px;
}

.part_poppover_title {
  color: #FFFFFF;
  display: flex;
  text-align: center;

  div {
    flex: 1;
  }
}

.part_poppover_data {
  color: #01f0ff;
  display: flex;

  div {
    flex: 1;
    padding-top: 8px;
    text-align: center;
  }
}

.more {
  text-align: center;
  color: $LABEL_COLOR;
  padding-top: 10px;
  cursor: pointer;
}

.part1 {
  left: 5px;
  top: 5px;
}

.part2 {
  left: 45px;
  top: 105px;
}

.part3 {
  left: 110px;
  top: 120px;
}

.part4 {
  left: 150px;
  top: 25px;
}

.part5 {
  left: 140px;
  top: 115px;
}

.part6 {
  left: 230px;
  top: 30px;
}

.part7 {
  left: 210px;
  top: 142px;
}

.part8 {
  left: 270px;
  top: 147px;
}

.part9 {
  left: 280px;
  top: 25px;
}

.part10 {
  left: 330px;
  top: 70px;
}

.six_percent {
  width: 60%;
  height: 100%;
}

.four_percent {
  width: 40%;
  height: 100%;
  display: flex;
  flex-direction: column;
  //align-items: center;
  justify-content: center;
  cursor: pointer;
}

.chart_name {
  font-size: 14px;
}

.chart_num {
  color: #01f0ff;
  font-size: 18px;
}

.chart_unit {
  font-size: 14px;
}

::v-deep .el-input--medium {
  width: 100%;
}

::v-deep .el-input--medium .el-input__inner {
  border-color: $LABEL_COLOR;
  height: 32px;
  line-height: 32px;
  background: rgba(0, 0, 0, 0);
}

.phase:not(:last-child) .phase_item {
  height: 16px;
  border-bottom: #01f0ff 2px dashed;
  border-left: #01f0ff 2px solid
}

.phase:last-child .phase_item {
  height: 16px;
  //border-bottom: #01f0ff 2px dashed;
  border-left: #01f0ff 2px solid
}

.phase_name {
  color: #01f0ff;
  margin-left: -10px;
  font-size: 12px;
}

.flexCenter {
  height: 100%;
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}


.element1 {
  width: 180px;
  height: 180px;
  background-color: rgba(78, 167, 249, 1);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.child1 {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background: linear-gradient(180deg, #141414 0%, #141414 0%, #161922 47%, #141414 100%, #141414 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: 500;
}

.element2 {
  width: 180px;
  height: 180px;
  background-color: rgba(0, 255, 255, 1);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.child2 {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background: linear-gradient(180deg, #141414 0%, #141414 0%, #161922 47%, #141414 100%, #141414 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
