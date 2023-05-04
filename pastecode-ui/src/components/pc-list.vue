<template>
  <div id="about" v-infinite-scroll="load">
    <ul class="infinite-list">
      <li v-for="i in count" class="infinite-list-item">{{ i }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "pcList",
  data () {
    return {
      count: 0
    }
  },
  methods: {
    load () {
      this.count += 2
      console.log(new Date().getTime())
    }
  }
}
</script>

<style scoped>
#about {
  height: 100vh;
  overflow: auto;
}

.infinite-list {
  /*height: 100vh;*/
}

.infinite-list .infinite-list-item {
  /* 设置弹性布局 */
  display: flex;
  /* 垂直居中 */
  align-items: center;
  /* 水平居中 */
  justify-content: center;
  height: 50px;
  background: #e8f3fe;
  margin: 10px;
  color: #e43333
}
</style>