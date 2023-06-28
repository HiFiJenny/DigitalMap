<template>
  <div>
    <svg-icon :icon-class="isFullscreen?'exit-fullscreen':'fullscreen'" @click="click"/>
  </div>
</template>

<script>
import screenfull from 'screenfull'

export default {
  name: 'Screenfull',
  data() {
    return {
      isFullscreen: false
    }
  },
  mounted() {
    this.init()
  },
  beforeDestroy() {
    this.destroy()
  },
  methods: {
    click() {
      if (!screenfull.isEnabled) {
        this.$message({message: '你的浏览器不支持全屏', type: 'warning'})
        return false
      }
      console.log(this)
      console.log(this.$route.path)
      if (this.$route.path === '/index') {
        let aa = this.$parent.$parent.$children[2].$el
        console.log(aa)
        screenfull.toggle(aa);
        console.log(this.isFullscreen)

      } else {
        screenfull.toggle()
      }

    },
    change() {
      this.isFullscreen = screenfull.isFullscreen
      if (this.$route.path === '/index') {

        if (this.isFullscreen) {
          console.log(1111)
          document.getElementsByClassName("ksh")[0].setAttribute('style','height:100%')

        } else {
          console.log('22')
          document.getElementsByClassName("ksh")[0].setAttribute('style','')

        }
      }
    },
    init() {
      if (screenfull.isEnabled) {
        screenfull.on('change', this.change)
      }
    },
    destroy() {
      if (screenfull.isEnabled) {
        screenfull.off('change', this.change)
      }
    }
  }
}
</script>

<style scoped>
.screenfull-svg {
  display: inline-block;
  cursor: pointer;
  fill: #5a5e66;;
  width: 20px;
  height: 20px;
  vertical-align: 10px;
}
</style>
