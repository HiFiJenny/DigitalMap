<template>
  <transition name="fade">
    <div class="el-dialog__wrapper" style="z-index: 1200;background: rgba(0, 0, 0,0.5);" @click.stop.prevent=""
         v-show="visible">
      <VueDragResize dragHandle=".el-dialog__header" @clicked="clickHandle"

                     :isActive="false" :w="w" :h="h" :x="x" :y="y" :isResizable="true" :key="w+'-'+h+'-'+x">
        <div role="dialog" aria-modal="true" aria-label="提示" class="el-custom-dialog"

             style="margin-top: 0 !important;height: 100% ;width: 100%;">
          <div class="el-dialog__header"><span class="el-dialog__title">
          {{ title }}
        </span>
            <button type="button" aria-label="Close"
                    @click="close"
                    class="el-dialog__headerbtn"><i
              class="el-dialog__close el-icon el-icon-close"></i></button>
          </div>
          <div class="el-dialog__body">
            <slot/>
          </div>
          <div class="el-dialog__footer">
            <div class="dialog-footer">
              <slot name="footer"></slot>
            </div>
          </div>
        </div>
      </VueDragResize>
      <!--      <div role="dialog" aria-modal="true" aria-label="提示" class="el-custom-dialog"

                 style="margin-top: 15vh; " :style="{width: width}">
              <div class="el-dialog__header"><span class="el-dialog__title">
                      {{ title }}
                    </span>
                <button type="button" aria-label="Close"
                        @click="close"
                        class="el-dialog__headerbtn"><i
                  class="el-dialog__close el-icon el-icon-close"></i></button>
              </div>
              <div class="el-dialog__body">
                <slot/>
              </div>
              <div class="el-dialog__footer">
                <div class="dialog-footer">
                  <slot name="footer"></slot>
                </div>
              </div>
            </div>-->
    </div>

  </transition>
</template>

<script>
import VueDragResize from 'vue-drag-resize';
import {debounce} from "@/utils";

export default {
  name: 'CustomDialog',
  components: {VueDragResize},
  data() {
    return {
      modal: null,
      w: 700,
      h: 500,
      x: 0,
      y: 0,
    }
  },
  props: {
    visible: {
      required: true,
      type: Boolean
    },
    width: {
      default: '50%',
      type: String,
    },
    title: {
      default: '提示',
      type: String,
    },
    beforeClose: {
      type: Function,
      default: () => {
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      let bw = document.body.clientWidth
      let bh = document.body.clientHeight
      this.w = 0.7 * bw
      this.h = 0.85 * bh
      this.x = (0.3 * bw) / 2
      this.y = (0.08 * bh)
      console.log('dilog', bh, this.h)

      this.$forceUpdate()
    })

    window.addEventListener("resize", this.resize())
  },
  destroyed() {
    window.removeEventListener("resize", this.resize())
  },
  methods: {
    resize() {
      return debounce(() => {
        console.log('onresize')
        let bw = document.body.clientWidth
        let bh = document.body.clientHeight
        this.w = 0.7 * bw
        this.h = 0.8 * bh
        this.x = (0.3 * bw) / 2
        this.y = (0.1 * bh)
        console.log('dialog', bh, this.h)
      }, 400, false)
    },
    close() {
      this.$emit('update:visible', false)
      this.beforeClose()
    },
    clickHandle(e) {
      e.stopPropagation()
      console.log('clickHandle')
      e.target.focus()
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

.el-custom-dialog {
  position: relative;

  //margin: 0 auto 50px;
  /* background: #dbddde !important; */
  background: #f9f9fa !important;
  -webkit-box-shadow: 0 1px 3px rgb(0 0 0 / 30%);
  box-shadow: 0 1px 3px rgba(0, 0, 0, .3);
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  /*  height: 80%;
    width: 50%;
    max-height: calc(100% - 50px);
    max-width: calc(100% - 50px);*/
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
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
  padding: 30px 20px;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
}

.el-dialog__footer {
  padding: 20px;
  padding-top: 10px;
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

::v-deep .vdr.active:before {
  outline: none;
}

::v-deep .vdr-stick {
  opacity: 0 !important;
}
</style>
