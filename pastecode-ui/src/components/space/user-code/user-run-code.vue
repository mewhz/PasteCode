<template>
  <div id="user-run-code">
    <el-empty :image-size="200" v-if="codeRunInfo === null || codeRunInfo.length === 0" description="暂无消息内容"></el-empty>
    <ul class="infinite-list" v-else>
      <li v-for="info in codeRunInfo" class="infinite-list-item">
        <span v-if="info.runError === ''">
        <el-link :underline="false" type="primary" @click="goCode(info.codeId)">代码</el-link>
          在 {{ info.runCreateDate }}
          <span class="error-item">运行报错</span>
        </span>
        <span v-if="info.runError !== ''">
        <el-link :underline="false" type="primary" @click="goCode(info.codeId)">代码</el-link>
          在 {{ info.runCreateDate }}
          <span class="success-item">成功运行</span>
        </span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "userRunCode",
  props: {
    userId: {
      type: Number,
    }
  },
  data() {
    return {
      codeRunInfo: []
    }
  },
  methods: {

    goCode(codeId) {
      this.$router.push(`/code/id/${codeId}`)
    },

    load() {

      this.$axios({
        method: "get",
        url: `${this.$url}/code/getUserRunCodeList/${this.userId}`
      }).then((response) => {

        console.log(response.data.data);

        this.codeRunInfo = response.data.data;
      });
    }
  },
  mounted() {
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
  color: #521b1b
}
.success-item {
  color: rgb(68, 157, 68);
}
.error-item {
  color: rgb(208, 84, 81);
}
li {
  font-size: 16px;
}
.el-link {
  font-size: 16px !important;
}
</style>