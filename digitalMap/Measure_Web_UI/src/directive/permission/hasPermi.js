import store from '@/store'
import {Message} from 'element-ui';

export default {
  inserted(el, binding, vnode) {
    const {value} = binding
    const all_permission = "*:*:*";
    const permissions = store.getters && store.getters.permissions

    if (value && value instanceof Array && value.length > 0) {
      const permissionFlag = value

      const hasPermissions = permissions.some(permission => {
        return all_permission === permission || permissionFlag.includes(permission)
      })

      if (!hasPermissions) {
        el.parentNode && el.parentNode.removeChild(el)
        //置灰而不删除
   /*     el.disabled = true
        el.style.backgroundColor = 'transparent'
        el.style.color = '#c0c4cc'
        el.style.cursor = 'not-allowed'
        let className = el.className
        if (className.indexOf('el-button--text') > -1) {
          el.style.borderColor = 'transparent'

        } else {
          el.style.borderColor = '#c0c4cc'

        }

        //el.__listeners
        el.addEventListener('click',(event)=>{
          Message.warning('暂无权限')
          event.preventDefault();
          event.stopPropagation()
          // event.stopImmediatePropagation()
          console.log('---')
        },true)*/
      }
    } else {
      throw new Error(`请设置操作权限标签值`)
    }
  }
}
