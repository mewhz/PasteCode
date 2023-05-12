<template>
  <div v-if="show" style="text-align: center;">
    <pc-header></pc-header>
    <h1 v-text="codeTitle">Code</h1>
    <span v-if="code.codeAuthorId !== 0">代码作者:
      <el-link :underline="false" @click="goSpace" >{{ code.userName }}</el-link>
      <el-link :underline="false" @click="edit" v-if="userId === (code.codeAuthorId.toString())">编辑</el-link>
    </span>
    <span v-if="code.codeAuthorId === 0">代码作者:游客
      <el-link :underline="false" @click="edit" v-if="userId === (code.codeAuthorId.toString())">编辑</el-link>
    </span>
    <span>分享时间：{{ code.codeCreateDate }}</span>
    <pc-code-status :code="Object.assign({}, this.code)"></pc-code-status>
    <pc-code :code="Object.assign({}, this.code)"></pc-code>

    <el-button type="primary" size="mini" @click="copyCode">点我复制代码</el-button>
    <el-button type="primary" size="mini" @click="downCode">点我下载源码</el-button>
    <el-button type="primary" size="mini" v-print="'#print'">点我下载PDF</el-button>

    <el-dialog title="编辑代码" :visible.sync="updateCodeVisible" center destroy-on-close>
      <update-code-form
          :key="new Date().getTime()"
          :code-prop="Object.assign({}, this.code)"
          @exit="exit"
          @reload="reload"></update-code-form>
    </el-dialog>

    <div style="text-align: left" v-if="this.isRun">
      <div style="margin: 5px">
        <span class="hint">输入</span>
        <el-card>
          <div>{{ input }}</div>
        </el-card>
      </div>
      <div style="margin-left: 5px">
        <span class="hint">输出</span>
        <el-card>
          <div v-for="(item, index) in output" :key="index">{{ item }}</div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import Prism from "prismjs";

import PcCode from "@/components/pc-code";
import '@/assets/js/iconfont'
import PcCodeStatus from "@/components/pc-code-status";
import UpdateCodeForm from "@/components/admin/codeManagement/UpdateCodeForm";
import Copy from "copy-to-clipboard";
import PcHeader from "@/components/pc-header";

export default {
  name: "Code",
  components: {PcHeader, PcCodeStatus, PcCode, UpdateCodeForm},
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
      isRun: false,
      input: '',
      output: '',
    }
  },
  methods: {

    downCode() {
      console.log("codeId", this.code.codeId);

      this.$axios({
        method: "get",
        url: `${this.$url}/code/download/${this.code.codeId}`,
        responseType: "blob"
      }).then((response) => {
        const cd = response.headers["content-disposition"];
        const title = cd.substring(cd.indexOf("'") + 2, cd.length);
        const fileName = decodeURI(decodeURI(title));

        let blob = new Blob([response.data]);
        let url = window.URL.createObjectURL(blob);
        let a = document.createElement('a');

        a.href = url;
        a.download = fileName;
        a.click();

        window.URL.revokeObjectURL(url);
      });
    },
    copyCode() {
      // 设置格式化类型，防止有些软件换行符不会被处理
      let flag = Copy(this.code.codeText, { format: 'text/plain' });
      if (flag) {
        this.$message({
          message: '复制成功, 快去粘贴给小伙伴吧!!',
          type: 'success',
          duration: 1200
        });
      }
      else {
        this.$message({
          message: '复制失败, 请联系管理员..',
          type: 'error',
          duration: 1200
        });
      }
    },

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

        let resp = response.data;

        this.code = resp;
        console.log("code", JSON.stringify(this.code));
        this.show = true;


        if (resp.codeTitle === undefined || resp.codeTitle === "" || resp.codeTitle === null) {
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

      await this.$axios({
        method: "get",
        url: `${this.$url}/run/isRun/${this.code.codeId}`
      }).then((response) => {
        let resp = response.data.data;
        if (resp.isRun) {
          this.output = resp.runError === "" ? resp.runOutput.split("\r\n") : resp.runError.split("\r\n");
          this.input  = resp.runInput;
        }
        this.isRun  = resp.isRun;
      });
    },

    goSpace() {
      this.$router.push("/space/" + this.code.userAccount);
    }


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
  margin-right: 12px;
}
span {
  margin-bottom: 10px;
  display: block;
  font-size: 20px;
}
h1 {
  margin-bottom: 10px;
  margin-top: 0;
}
.hint {
  font-size: 20px;
}
</style>