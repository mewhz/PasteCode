<template>
  <div>
  <el-collapse accordion v-model="activeNames">
    <el-collapse-item v-for="(value, index ) in codeArray"
                      :key="index"
                      :title="value.codeTitle"
                      :name="index">
      <el-link :underline="false" @click="jumpDetails(value.codeId)">
        <i class="el-icon-view"></i>查看详情
      </el-link>
      <pc-code :code="value"></pc-code>
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
import PcCode from "@/components/pc-code";

export default {
  name: "Title",
  components: {PcCode},
  data() {
    return {
      codeTitle: "",
      codeArray: [],
      total: 0,
      size: 3,
      current: 1,
      activeNames: []
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

      await this.getTotal();
      if (this.total === 0) {
        this.$message({
          message: '搜索的代码标题不存在!!',
          type: 'warning',
          duration: 1000
        });
        console.log("跳转");
        await this.$router.push("/");
      }
    },

    async pageTurning() {
      this.activeNames = [];
      await this.getCodes();
      this.$nextTick(() => {
        Prism.highlightAll();
      });

    },

    async getTotal() {
      await this.$axios.get(`${this.$url}/code/page/total/${this.codeTitle}`)
          .then((response) => {
            let resp = response.data;
            console.log(resp.data);
            this.total = resp.data;
          });
    },

    async getCodes() {
      await this.$axios.get(`${this.$url}/code/page/${this.codeTitle}/${this.current}`)
          .then((response) => {
            let resp = response.data;
            console.log(resp.data);
            this.codeArray = resp.data;
          });
    },

    next() {
      this.current ++;
      this.pageTurning();
    },

    previous() {
      this.current --;
      this.pageTurning();
    },

    currentChange(current) {
      this.current = current;
      this.pageTurning();
    },

    jumpDetails(codeId) {
      console.log(codeId);
      const routerURL = this.$router.resolve("/code/id/" + codeId);

      window.open(routerURL.href, "_blank");
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