import request from '@/utils/request'

// 查询字段配置列表
export function listConfig(query) {
  return request({
    url: '/system/field/config/list',
    method: 'get',
    params: query
  })
}

// 查询字段配置详细
export function getConfig(id) {
  return request({
    url: '/system/field/config/' + id,
    method: 'get'
  })
}

// 新增字段配置
export function addConfig(data) {
  return request({
    url: '/system/field/config',
    method: 'post',
    data: data
  })
}

// 修改字段配置
export function updateConfig(data) {
  return request({
    url: '/system/field/config',
    method: 'put',
    data: data
  })
}

// 删除字段配置
export function delConfig(id) {
  return request({
    url: '/system/field/config/' + id,
    method: 'delete'
  })
}

// 修改字段配置
export function updateSortConfig(data) {
  return request({
    url: '/system/field/config/order',
    method: 'post',
    data: data
  })

}
