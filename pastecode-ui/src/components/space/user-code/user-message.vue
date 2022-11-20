<template>
  <div id="user-message">
    <el-empty :image-size="200" v-if="codeStatusInfo === null || codeStatusInfo.length === 0" description="暂无消息内容"></el-empty>
    <ul class="infinite-list" v-else>
      <li v-for="info in codeStatusInfo" class="infinite-list-item">
        <span v-if="info.statusType === 1">
          用户 {{ info.userName }}
<!--          <el-link :underline="false" type="primary" @click="goCode(info.codeId)">代码</el-link>-->
          在 {{ info.statusCreateDate }} 赞了我的
        <el-link :underline="false" type="primary" @click="goCode(info.codeId)">代码</el-link>
        </span>
        <span v-if="info.statusType === 0">
          用户 {{ info.userName }} 在 {{ info.statusCreateDate }} 收藏了我的
        <el-link :underline="false" type="primary" @click="goCode(info.codeId)">代码</el-link>
        </span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "UserMessage",
  props: {
    userId: {
      type: Number,
    }
  },
  data() {
    return {
      codeStatusInfo: []
    }
  },
  methods: {

    goUser(userId) {
      this.$router.push(`/user/space/${userId}`)
    },

    goCode(codeId) {
      this.$router.push(`/code/id/${codeId}`)
    },

    load() {

      console.log("id", this.userId);

      this.$axios({
        method: "get",
        url: `${this.$url}/code/getStatusList/${this.userId}`
      }).then((response) => {
        this.codeStatusInfo = response.data.data;
      });
    }
  },
  mounted() {
    console.log("user-message", new Date().getTime())
    this.load();
    this.$forceUpdate();
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