<template>
  <div>
    <h1 v-text="codeTitle">Code</h1>
<!--    <div class="line-numbers rainbow-braces match-braces no-brace-hover no-brace-select">-->
<!--      <pre><code :class=codeClass v-text="codeText"/></pre>-->
<!--    </div>-->
    <MyCode :code-text="codeText" :code-class="codeClass"></MyCode>
  </div>
</template>

<script>

import Prism from "prismjs";

export default {
  name: "Code",
  data() {
    return {
      id: "",
      codeText: '',
      codeClass: '',
      codeTitle: '代码标题:'
    }
  },
  methods: {
    load() {
      let tokenNames  = localStorage.getItem("tokenName");
      let tokenValue = localStorage.getItem("tokenValue");

      console.log("tokenValue:", localStorage.getItem("tokenName"))
      let headers = {};

      headers[tokenNames] = tokenValue;

      console.table(headers);

      this.$axios.get('http://127.0.0.1:9090/code/id/' + this.id, {
        headers: headers,
      }).then((res) => {

        res = res.data;
        if (res.responseCode === 50000) {
          console.log("path:", this.$route.path);
          localStorage.setItem("path", this.$route.path);
          this.$router.push("/login");
          return;
        }

        this.codeText = res.codeText;
        this.codeClass = `language-${res.codeType} show-language`;

        if (this.codeText === undefined || this.codeText === ""){
          this.$router.push("/");
        }

        if (res.codeTitle === undefined || res.codeTitle === "") {
          this.codeTitle = "";
        }
        else {
          this.codeTitle += res.codeTitle;
        }

        this.$nextTick(() => {
          // 将回调函数放到下一个节点执行
          Prism.highlightAll();
          // this.$forceUpdate();
        });

      }).catch((err) => {
        console.log(err);
      });
    }
  },
  // 初始化时加载的方法
  created() {
    // 获取到传递的参数
    this.id = this.$route.params.id;
    this.load();
  }
}
</script>

<style scoped>

</style>