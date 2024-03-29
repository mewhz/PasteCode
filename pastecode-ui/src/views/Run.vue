<template>
  <div class="ace-container">
    <pc-header></pc-header>
      <el-card class="item">
        <label class="title">语言</label>
        <el-select class="value" v-model="modePath" @change="handleModelPathChange" size="mini" value-key="name">
          <el-option v-for="mode in modeArray"
                     :key="mode.name"
                     :label="mode.name"
                     :value="mode.path">
          </el-option>
        </el-select>
        <el-button icon="el-icon-refresh"
                   size="small"
                   round
                   style="margin-left: 5px"
                   @click="refresh">重置</el-button>
        <el-button icon="el-icon-document"
                   size="small"
                   round
                   style="margin-left: 5px"
                   @click="examples">A + B 示例代码</el-button>
      </el-card>
    <div class="ace-editor" ref="ace"></div>
    <el-button @click="send" style="margin: 5px" type="primary">运行代码</el-button>
    <div style="margin: 5px">
      <span class="hint">输入</span>
      <el-card>
        <el-input v-model="input"></el-input>
      </el-card>
    </div>
    <div style="margin-left: 5px">
      <span class="hint">输出</span>
      <el-card>
        <div v-if="runFlag">运行中<i class="el-icon-loading"></i></div>
        <div v-for="(item, index) in output" :key="index" v-if="!runFlag">{{ item }}</div>
      </el-card>
    </div>
  </div>
</template>

<script>
import ace from 'ace-builds'
import 'ace-builds/src-noconflict/snippets/c_cpp'
import 'ace-builds/src-noconflict/snippets/java'
import 'ace-builds/webpack-resolver'
import 'ace-builds/src-noconflict/ext-language_tools'
import 'ace-builds/src-noconflict/theme-monokai'
import 'ace-builds/src-noconflict/mode-javascript'
import PcHeader from "@/components/pc-header";


const wrapArray = [{
  name: '开启',
  value: true
}, {
  name: '关闭',
  value: false
}]

const modeArray = [{
  name: 'C/C++',
  path: 'ace/mode/c_cpp'
}, {
  name: 'Java',
  path: 'ace/mode/java'
}, {
  name: 'Python',
  path: 'ace/mode/python'
}]

export default {
  name: "Run",
  components: {PcHeader},
  props: {
    value: String
  },
  mounted () {
    this.aceEditor = ace.edit(this.$refs.ace, {
      maxLines: 30,
      minLines: 20,
      fontSize: 14,
      value: this.value ? this.value : '',
      theme: this.themePath,
      mode: this.modePath,
      wrap: this.wrap,
      tabSize: 4
    })
    // 激活自动提示
    this.aceEditor.setOptions({
      enableSnippets: true,
      enableLiveAutocompletion: true,
      enableBasicAutocompletion: true
    })
    // this.aceEditor.getSession().on('change', this.change)
    this.userId = localStorage.getItem("userId");
  },
  data () {
    return {
      runFlag: false,
      aceEditor: null,
      toggle: false,
      wrap: true,
      themePath: 'ace/theme/monokai',
      modePath: 'ace/mode/c_cpp',
      modeArray: modeArray,
      wrapArray: wrapArray,
      input: '',
      output: '',
      userId: 0
    }
  },
  methods: {
    examples() {

      let codeText = "";
      console.log("abc")
      if (this.modePath === "ace/mode/c_cpp")
        codeText = "#include <bits/stdc++.h>\n" +
            "using namespace std;\n" +
            "int main() {\n" +
            "\tint a, b;\n" +
            "\tcin >> a >> b;\n" +
            "\tcout << a + b << endl;\n" +
            "\treturn 0;\n" +
            "} "
      else if (this.modePath === "ace/mode/java")
        codeText = "import java.util.Scanner;\n" +
            "\n" +
            "public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "        Scanner input = new Scanner(System.in);\n" +
            "\n" +
            "        int a = input.nextInt();\n" +
            "        int b = input.nextInt();\n" +
            "\n" +
            "        System.out.println(a + b);\n" +
            "    }\n" +
            "}"
      else if (this.modePath === "ace/mode/python")
        codeText = "(a, b) = input().split(' ')\n" +
            "print(int(a) + int(b))\n"

      this.aceEditor.getSession().setValue(codeText);
    },

    refresh() {
      this.aceEditor.getSession().setValue('');
    },

    send() {
      console.log(this.userId);
      if (this.userId === null || this.userId === 0) {
        this.$confirm('您还未登录, 是否登录?', '系统提示', {
          confirmButtonText: '登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          localStorage.setItem("path", "/run");
          this.$router.push('/login');
        });
        return;
      }

      this.runFlag = true;

      let codeText = this.aceEditor.getSession().getValue();
      let codeType;
      this.userId = localStorage.getItem("userId");

      if (this.modePath === "ace/mode/c_cpp")
        codeType = "cpp";
      else if (this.modePath === "ace/mode/java")
        codeType = "java";
      else if (this.modePath === "ace/mode/python")
        codeType = "python";


      this.$axios({
        url: `${this.$url}/run/`,
        method: 'post',
        data: {
          "codeText": codeText,
          "codeType": codeType,
          "runInput": this.input,
          "codeAuthorId": this.userId
        }
      }).then((response) => {
        console.log(response.data);

        if (response.data.flag === true) {
          let resp = response.data.data;


          console.log(resp.runError === "")

          this.output = resp.runError === "" ? resp.runOutput.split("\r\n") : resp.runError.split("\r\n");

          this.runFlag = false;

        }
        console.log(this.output);
      })
    },

    toggleConfigPanel () {
      this.toggle = !this.toggle
    },
    change () {
      this.$emit('input', this.aceEditor.getSession().getValue())
    },
    handleModelPathChange (modelPath) {
      this.aceEditor.getSession().setMode(modelPath)
    },
    handleWrapChange (wrap) {
      this.aceEditor.getSession().setUseWrapMode(wrap)
    }
  }
}
</script>

<style lang='scss' scoped>
// /deep/ 深度选择器, 根据父元素深度的找到需要改变的子元素
.el-card /deep/ .el-card__body {
  padding: 8px!important;
}
.hint {
  font-size: 20px;
}
.ace-container {
  position: relative;

  .config-panel {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 50%;
    height: 100%;
    overflow: scroll;
    box-shadow: grey -5px 2px 3px;
    background-color: rgba(255, 255, 255, 0.5);
    z-index: 1;
  }

  .bookmarklet {
    //position: absolute;
    right: 0;
    bottom: 0;
    width: 20px;
    height: 20px;
    z-index: 2;
    cursor: pointer;
    border-width: 9px;
    border-style: solid;
    border-color: lightblue gray gray rgb(206, 173, 230);
    border-image: initial;
  }
}
.item {
  float: right;
  width: 100%;
}
</style>
