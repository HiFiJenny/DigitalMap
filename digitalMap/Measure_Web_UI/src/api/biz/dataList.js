import request from '@/utils/request'
import {Loading, Message} from "element-ui";
import {blobValidate, tansParams} from "@/utils/ruoyi";
import errorCode from "@/utils/errorCode";
import service from "@/utils/request";

// 查询字段配置列表
export function listData(query) {
  return request({
    url: '/system/data/list',
    method: 'post',
    data: query
  })
}

// 查询字段配置列表
export function listDataByEquipment(query) {
  return request({
    url: '/system/data/equipment/list',
    method: 'post',
    data: query
  })
}


// 查询工作内容
export function getWorkInfo(query) {
  return request({
    url: '/system/data/workInfo',
    method: 'get',
    params: query
  })
}


// 修改字段配置
export function updateData(data) {
  return request({
    url: '/system/data',
    method: 'put',
    data: data
  })
}

// 删除字段配置
export function delConfig(id) {
  return request({
    url: '/system/data/' + id,
    method: 'delete'
  })
}


// 下载数据
export function downloadData(url, params, filename, config) {

  let downloadLoadingInstance = Loading.service({
    text: "正在下载数据，请稍候",
    spinner: "el-icon-loading",
    background: "rgba(0, 0, 0, 0.7)",
  })
  return request.post(url, params, {
    timeout: 120000,
    // transformRequest: [(params) => { return tansParams(params) }],
    // headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    responseType: 'blob',
    ...config
  }).then(async (data) => {
    const isLogin = await blobValidate(data);
    if (isLogin) {
      const blob = new Blob([data])
      saveAs(blob, filename)
    } else {
      const resText = await data.text();
      const rspObj = JSON.parse(resText);
      const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode['default']
      Message.error(errMsg);
    }
    downloadLoadingInstance.close();
  }).catch((r) => {
    console.error(r)
    Message.error('下载文件出现错误，请联系管理员！')
    downloadLoadingInstance.close();
  })

}


