<template>
  <div>
    <svg class="icon" aria-hidden="true" @click="up" style="font-size: 2rem">
      <use xlink:href="#icon-dianzan" v-if="message"></use>
      <use xlink:href="#icon-dianzanxin" v-if="!message"></use>
    </svg>
  </div>
</template>
<script>

// import '@/assets/css/iconfont.css'

import '@/assets/js/iconfont'

export default {
  name: 'About',
  data() {
    return {
      message: true,
    }
  },
  methods: {
    up(key) {
      this.message = ! this.message;
    }
  }
}
</script>

<style scoped>

.icon {
   width: 1em;
   height: 1em;
   vertical-align: -0.15em;
   fill: currentColor;
   overflow: hidden;
 }
</style>