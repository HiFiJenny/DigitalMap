//阶段预设颜色
export const predefineColors = {
  '航前准备': '#A966A9',
  '开车': '#9665E6',
  '操作测试': '#A281D9',
  '滑出': '#C1A7EA',
  '起飞': '#9DD4FF',
  '初始爬升': '#67BFFF',
  '爬升': '#40AEFF',
  '巡航': '#0F8FEC',
  '下降': '#2CA388',
  '进近': '#24B865',
  '复飞': '#88CDA5',
  '最终进近': '#BED530',
  '着陆': '#F6C739',
  '滑入': '#EBA865',
  '关车': '#EB7F65',
  '': 'rgba(0,0,0,0)',
}

export const FEATURE_FLIGHT_CH_EN = {
  '航前准备': 'PREFLIGHT',
  '开车': 'ENGINE START',
  '操作测试': 'TEST',
  '滑出': 'TAXI OUT',
  '起飞': 'TAKE OFF',
  '初始爬升': 'INITIAL CLIMB',
  '爬升': 'CLIMB',
  '巡航': 'CRUISE',
  '下降': 'DESCENT',
  '进近': 'APPROACH',
  '复飞': 'GO AROUND',
  '最终进近': 'FINAL APPROACH',
  '着陆': 'LANDING',
  '滑入': 'TAXI IN',
  '关车': 'ENGINE  STOP',
  '': 'UNKNOWN',
}
//时序参数 航段分布
export const FEATURE__FLIGHT_PHASE_CN = '_FLIGHT_PHASE_CN'


//计算优先级
export const PRIORITY = [{
  label: '高',
  value: 1
}, {
  label: '中',
  value: 2
}, {
  label: '低',
  value: 3
}]

//数据状态
export const DATA_STATUS={
  '1': {'info': '成功', 'type': 'primary'},
  '2': {'info': '失败', 'type': 'warning'},
}

//译码任务状态
export const DECODE_TASK_STATUS={
  '1': {'info': '待执行', 'type': 'info'},
  '2': {'info': '进行中', 'type': 'primary'},
  '3': {'info': '已完成', 'type': 'success'},
  '4': {'info': '已失败', 'type': 'danger'},
}
