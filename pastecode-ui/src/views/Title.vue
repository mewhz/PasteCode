<template>
  <div>
  <el-collapse accordion>
    <el-collapse-item v-for="(value, index ) in codeArray" :key="index" :title="value.codeTitle">
      <el-link :underline="false"><i class="el-icon-view"></i>查看详情</el-link>
      <MyCode :code-text="value.codeText" :code-class='`language-${value.codeType} show-language`'></MyCode>
    </el-collapse-item>
  </el-collapse>
    <el-pagination
        background
        layout="prev, pager, next"
        :total=total
        :page-size="size"
        :current-page="current"
        @prev-click="previous"
        @next-click="next"
        @current-change="currentChange"
    >
    </el-pagination>
  </div>
</template>

<script>
import Prism from "prismjs";

export default {
  name: "Title",
  data() {
    return {
      codeTitle: "",
      codeArray: [],
      total: 0,
      size: 3,
      current: 1
    };
  },
  created() {
    this.codeTitle = this.$route.params.codeTitle;
    this.init();
  },
  methods: {
    async init() {
      await this.getCodes();
      this.$nextTick(() => {
        // 将回调函数放到下一个节点执行
        Prism.highlightAll();
        // this.$forceUpdate();
      });

      this.getTotal();
    },

    getTotal() {
      this.$axios.get('http://127.0.0.1:9090/code/page/total/' + this.codeTitle)
          .then((response) => {
            console.log(response.data);
            this.total = response.data;
          });
    },

    async getCodes() {
      await this.$axios.get(`http://127.0.0.1:9090/code/page/${this.codeTitle}/${this.current}`)
          .then((response) => {
            console.log(response.data);
            this.codeArray = response.data;
          });
    },

    next() {
      this.current ++;
      this.getCodes();
    },

    previous() {
      this.current --;
      this.getCodes();
    },

    currentChange(current) {
      this.current = current;
      this.getCodes();
    }

  }
}
</script>

<style scoped>
.el-pagination{
  margin-top: 20px;
  text-align: center;
  /*position: fixed;*/
}
</style>