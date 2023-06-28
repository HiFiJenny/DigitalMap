import request from '@/utils/request'

// 获取全量设备
export function statisticAllequipment(query) {
  return request({
    url: '/system/statistic/equipment',
    method: 'get',
    params: query
  })
}
// 获取全量机型
export function statisticAllModel(query) {
  return request({
    url: '/system/statistic/allModel',
    method: 'get',
    params: query
  })
}

// 获取全量架次
export function statisticAllPlaneNo(query) {
  return request({
    url: '/system/statistic/allPlaneNo',
    method: 'get',
    params: query
  })
}

// 获取配置机型
export function statisticConfigModel (query) {
  return request({
    url: '/system/statistic/configModel',
    method: 'get',
    params: query
  })
}

// 获取配置架次
export function statisticConfigPlaneNo (query) {
  return request({
    url: '/system/statistic/configPlaneNo',
    method: 'get',
    params: query
  })
}

// 按飞机部位查询聚合数据
export function statisticAreaCount(query) {
  return request({
    url: '/system/statistic/area/count',
    method: 'post',
    data: query
  })
}
// 按飞机部位查询聚合数据列表
export function statisticAreaList(query) {
  return request({
    url: '/system/statistic/area/list',
    method: 'post',
    data: query
  })
}




// 查询- 具备能力情况
export function statisticExperimentCapacity(query) {
  return request({
    url: '/system/statistic/experiment/capacity',
    method: 'post',
    data: query
  })
}


// 统计-设备应用情况 equipmentNames=游标卡尺
export function statisticEquipmentApply(query) {
  return request({
    url: '/system/statistic/equipment/apply',
    method: 'post',
    data: query
  })
}

// 统计-研制过程测量环节和参数分布
export function statisticDevelopStage(query) {
  return request({
    url: '/system/statistic/develop/stage',
    method: 'post',
    data: query
  })
}

// 统计-各层级试验的测量过程数量和参数数量
export function statisticExperimentLevel(query) {
  return request({
    url: '/system/statistic/experiment/level',
    method: 'post',
    data: query
  })
}
// 统计-各类别试验的测量过程数量和参数数量
export function statisticExperimentClassify(query) {
  return request({
    url: '/system/statistic/experiment/classify',
    method: 'post',
    data: query
  })
}
// 统计-各系统测量过程数量和参数数量
export function statisticAircraftSystem(query) {
  return request({
    url: '/system/statistic/aircraft/system',
    method: 'post',
    data: query
  })
}

// 统计-各机型测量过程数量和参数数量
export function statisticExperimentParam(query) {
  return request({
    url: '/system/statistic/experiment/param',
    method: 'post',
    data: query
  })
}
// 统计-各型号关键参数占比
export function statisticImportant(query) {
  return request({
    url: '/system/statistic/important',
    method: 'post',
    data: query
  })
}
