<template>
  <div id="user-collect-code">
    <el-empty :image-size="200" v-if="codeCollectList.length === 0" description="暂无收藏内容"></el-empty>
    <ul class="infinite-list" v-else>
      <li v-for="info in codeCollectList" class="infinite-list-item">
        <span>
          {{ info.userName }}的
        <el-link :underline="false" type="primary" @click="goCode(info.codeId)">{{ info.codeTitle }}</el-link>
          收藏于 {{ info.statusCreateDate }}
        </span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "userCollectCode",
  props: {
    userId: {
      type: Number
    }
  },
  data() {
    return {
      codeCollectList: [],
    }
  },
  methods: {

    goCode(codeId) {
      this.$router.push(`/code/id/${codeId}`)
    },

    load() {
      this.$axios({
        method: "get",
        url: `${this.$url}/code/getUserCollectList/${this.userId}`
      }).then((response) => {
        this.codeCollectList = response.data.data;
        console.log(this.codeCollectList);
      });
    }
  },
  created() {
    this.load();
  }
}
</script>

<style scoped>
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

li {
  font-size: 16px;
}
.el-link {
  font-size: 16px !important;
}
</style>