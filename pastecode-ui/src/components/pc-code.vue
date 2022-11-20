<template>
  <div class="line-numbers rainbow-braces match-braces no-brace-hover no-brace-select">
    <pre id="print"><code :class='codeClass' v-text="code.codeText"/></pre>
    <el-button type="primary" size="mini" @click="copyCode">点我复制代码</el-button>
    <el-button type="primary" size="mini" @click="downCode">点我下载源码</el-button>
    <el-button type="primary" size="mini" v-print="'#print'">点我下载PDF</el-button>
  </div>
</template>
<script>

import 'prismjs/components/prism-java.js';
import 'prismjs/components/prism-c.min.js';
import 'prismjs/components/prism-cpp.min.js';
import 'prismjs/plugins/match-braces/prism-match-braces.js';
import 'prismjs/plugins/show-language/prism-show-language.js';
import Copy from 'copy-to-clipboard'
import Prism from "prismjs";

export default {
  name: "PcCode",
  props: {
    code: {
      type: Object,
      required: true
    },
  },
  data() {
    return {
      codeClass: `language-${this.code.codeType} show-language`
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
    doPrint() {

      console.log("print");

      let bdhtml = window.document.body.innerHTML;//获取当前页的html代码

      let sprnstr = "<pre>";//设置打印开始区域

      let eprnstr = "</pre>";//设置打印结束区域

      let prnhtml = bdhtml.substring(bdhtml.indexOf("<pre>") + 5, bdhtml.indexOf("</pre>")); //从开始代码向后取html

      window.document.body.innerHTML = prnhtml;

      window.print();

      window.document.body.innerHTML = bdhtml;

    },
    copyCode() {
      // 设置格式化类型，防止有些软件换行符不会被处理
      let flag = Copy(this.codeText, { format: 'text/plain' });
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
    }
  },
  created() {
    this.$nextTick(() => {
      console.log("next");
      // 将回调函数放到下一个节点执行
      Prism.highlightAll();
    });
  }
}
</script>

<style scoped>
.el-button {
  margin-left: 15px;
}
</style>