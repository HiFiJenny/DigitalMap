<template>
  <transition name="fade">
    <div class="el-message-box__wrapper" style="z-index: 1200;background: rgba(0, 0, 0,0.5);"
         v-show="visible">
      <div  class="el-message-box"
           style=" width:420px;">
        <div class="el-message-box__header">
          <span class="el-message-box__title">
          {{ title }}
        </span>
          <!--          <button type="button" aria-label="Close"
                            @click="close"
                            class="el-dialog__headerbtn"><i
                      class="el-dialog__close el-icon el-icon-close"></i></button>
                  </div>-->
          <div class="el-dialog__body">
            <slot/>
          </div>
          <div class="el-dialog__footer">
            <div class="dialog-footer">
              <slot name="footer">
                <el-button type="primary" :disabled="isDisabled" @click="confirm">{{seconds===0?'确 定':seconds+'s'}}</el-button>
                <el-button @click="cancel">取 消</el-button>
              </slot>
            </div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: 'index',
  props: {
    visible: {
      required: true,
      type: Boolean,
      default: false
    },
    title: {
      default: '提示'
    },
    countDownSeconds: {
      type: Number,
      default: 3

    }
  },
  data() {
    return {
      seconds: this.countDownSeconds,//设置初始倒计时
      isDisabled: true,
      interval: null,

    }

  },
  created() {

  },
  methods: {
    confirm() {
      this.$emit('confirm')
    },
    cancel() {
      this.$emit('cancel')
    },
  },
  watch: {
    visible: function f(newVal) {
      if (newVal) {
        let that = this
        this.isDisabled = true
        this.interval = setInterval(function () {
          --that.seconds
          if (that.seconds === 0) {
            that.isDisabled = false
            window.clearInterval(that.interval)
          }
        }, 1000)
      }else {
        if (this.interval){
          this.seconds = this.countDownSeconds
          this.isDisabled = true
          window.clearInterval(this.interval)
        }
      }

    }
  }

}
</script>

<style lang="scss" scoped>
.el-dialog__wrapper {
  opacity: 1;
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  overflow: auto;
  margin: 0;
  transition: all ease-in-out 0.2s;
}

.el-message-box {
  display: inline-block;
  width: 420px;
  padding-bottom: 10px;
  vertical-align: middle;
  background-color: #FFFFFF;
  border-radius: 4px;
  border: 1px solid #e6ebf5;
  font-size: 18px;
  -webkit-box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
  text-align: left;
  overflow: hidden;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
}

.el-dialog__header {
  padding: 20px 20px 10px;
  background-color: #2e2e56;
  cursor: move;
}

.el-custom-dialog > .el-dialog__body {
  overflow: auto !important;
}

.el-dialog__body {
  padding: 10px 15px;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
}

.el-dialog__footer {
  padding: 5px 15px 0;
  text-align: right;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}

.el-dialog__headerbtn {
  position: absolute;
  top: 20px;
  right: 20px;
  padding: 0;
  background: transparent;
  border: none;
  outline: none;
  cursor: pointer;
  font-size: 16px;
}

.dialog_modal {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  opacity: 0.5;
  background: #000000;
}
</style>
