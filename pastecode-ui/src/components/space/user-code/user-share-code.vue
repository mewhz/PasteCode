<template>
  <div id="user-share-code">
    这里是用户分享的所有代码
    <el-collapse accordion>
      <el-collapse-item v-for="(value, index ) in dataList"
                        :key="index"
                        :title="value.codeTitle === null ? '无标题': value.codeTitle"
                        :name="index"
                        style="text-align: left !important;">
        <el-link :underline="false" @click="jumpDetails(value.codeId)">
          <i class="el-icon-view"></i>查看详情
        </el-link>
        <pc-code :code="value"></pc-code>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>

import PcCode from "@/components/pc-code";
export default {
  name: "UserShareCode",
  components: {PcCode},
  props: {
    userId: {
      type: Number,
    }
  },
  data() {
    return {
      dataList: [],
    }
  },
  methods: {

    jumpDetails(codeId) {
      const routerURL = this.$router.resolve("/code/id/" + codeId);

      window.open(routerURL.href, "_blank");
    },

    async load() {


      await this.$axios({
        method: "get",
        url: `${this.$url}/code/share/${this.userId}`
      }).then((response) => {
        this.dataList = response.data.data;
      });
    },

    async init() {
      console.log(this.userId);
      await this.load();
    }
  },
  created() {
    this.init();
  }
}
</script>

<style scoped>
>>> .el-collapse-item__content {
  padding-bottom: 0;
}
</style>