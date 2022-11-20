<template>
  <div v-if="show" style="text-align: center;">
    <h1 v-text="codeTitle">Code</h1>
    <span>代码作者：{{ code.userName }}
      <el-link :underline="false" @click="edit" v-if="userId === (code.codeAuthorId.toString())">编辑</el-link>
    </span>
    <span>分享时间：{{ code.codeCreateDate }}</span>
    <pc-code-status :code="Object.assign({}, this.code)"></pc-code-status>
    <pc-code :code="Object.assign({}, this.code)"></pc-code>


    <el-dialog title="编辑代码" :visible.sync="updateCodeVisible" center destroy-on-close>
      <update-code-form
          :key="new Date().getTime()"
          :code-prop="Object.assign({}, this.code)"
          @exit="exit"
          @reload="reload"></update-code-form>
    </el-dialog>
  </div>
</template>

<script>
import Prism from "prismjs";

import PcCode from "@/components/pc-code";
import '@/assets/js/iconfont'
import PcCodeStatus from "@/components/pc-code-status";
import UpdateCodeForm from "@/components/admin/codeManagement/UpdateCodeForm";

export default {
  name: "Code",
  components: {PcCodeStatus, PcCode, UpdateCodeForm},
  data() {
    return {
      userId: localStorage.getItem("userId"),
      updateCodeVisible: false,
      id: "",
      codeText: '',
      codeClass: '',
      codeTitle: ':',
      code: {},
      show: false,
    }
  },
  methods: {

    edit() {
      console.log("abc");
      this.updateCodeVisible = true;
    },

    exit() {
      this.updateCodeVisible = false;
    },
    async reload() {
      await this.load();
      this.updateCodeVisible = false;
    },

    async load() {
      await this.$axios({
        method: "get",
        url: `${this.$url}/code/id/${this.id}`,
      }).then((response) => {
        response = response.data;
        // if (response.flag === false) {
        //   console.log("path:", this.$route.path);
        //   localStorage.setItem("path", this.$route.path);
        //   this.$router.push("/login");
        //   return;
        // }

        let resp = response.data;

        this.code = resp;
        console.log("code", JSON.stringify(this.code));
        this.show = true;

        // if (this.codeText === undefined || this.codeText === ""){
        //   this.$router.push("/");
        // }

        if (resp.codeTitle === undefined || resp.codeTitle === "") {
          this.codeTitle = "";
        }
        else {
          this.codeTitle = "代码标题:" + resp.codeTitle;
        }

        this.$nextTick(() => {
          // 将回调函数放到下一个节点执行
          Prism.highlightAll();
          // this.$forceUpdate();
        });

      })
    },


  },
  // 初始化时加载的方法
  async created() {
    // 获取到传递的参数
    this.id = this.$route.params.id;
    await this.load();
    console.log("userId", typeof this.userId, "codeAuthorId", typeof this.code.codeAuthorId)
    console.log(this.userId === (this.code.codeAuthorId.toString()))
  }
}
</script>

<style scoped>
.el-link {
  font-size: 20px !important;
}
span {
  margin-bottom: 10px;
  display: block;
  font-size: 20px;
}
h1 {
  margin-bottom: 10px;
}
</style>