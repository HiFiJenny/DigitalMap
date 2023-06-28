<template>
  <div>
    <el-input style="width: 90px" :disabled="disable" v-model="min" :controls="false" @change="rangeChange" @blur="handleBlurFrom"></el-input>
    <span style="padding: 0 8px">-</span>
    <el-input style="width: 90px" :disabled="disable" v-model="max" :controls="false" @change="rangeChange" @blur="handleBlurFrom"></el-input>
  </div>
</template>

<script>
export default {
  name: 'index',
  props: {
    disable: {
      default: false
    },
    range: {
      required: true,
      default: []
    }
  },
  data() {
    return {
      min: null,
      max: null,
    }
  },
  created() {
    if (!Array.isArray(this.range)) {
      throw new Error("参数类型错误,仅支持包含数值的数组类型！")
    }
    if (this.range && this.range.length > 0) {
      this.min = this.range[0]
      this.max = this.range.length > 1 ? this.range[1] : null
    }
  },
  methods: {
    handleBlurFrom(){
      this.rangeChange()
    },
    rangeChange() {
      try {
        let regNeg = /^[-\\+]?([0-9]+\\.?)?[0-9]+$/;
        if (this.min && !regNeg.test(this.min)) {
          this.$message.warning("请输入合法值")
          this.$nextTick(() => {
            this.min = null

          })
          return
        }
        if (this.max && !regNeg.test(this.max)) {
          this.$message.warning("请输入合法值")
          this.$nextTick(() => {
            this.max = null
          })
          return
        }
        if (this.min && this.max && parseFloat(this.min) > parseFloat(this.max)) {
          this.$message.warning("最小值不能大于最大值")
          this.$nextTick(() => {
            this.min = null
          })
          return;
        }

        let min = this.min ? parseFloat(this.min) : null
        let max = this.max ? parseFloat(this.max) : null
        console.log([min, max])
        this.$emit('change', [min, max])

      } catch (e) {
        console.log(e)
        this.$message.warning("请输入合法值")

      }


    }
  }
}
</script>

<style lang="scss" scoped>

</style>
