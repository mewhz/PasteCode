<template>
  <div id="update-code-form">
    <el-form :model="code" :rules="rules" label-width="auto" ref="code">
      <el-form-item label="用户昵称">
        <el-input v-model="code.userName" placeholder="请输入用户昵称" size="medium" disabled></el-input>
      </el-form-item>
      <el-form-item label="代码标题" prop="codeTitle" id="codeTitle">
        <el-input v-model="code.codeTitle" placeholder="代码标题 (选填)" size="medium"></el-input>
      </el-form-item>
      <el-form-item id="select" label="代码语言" required>
        <el-select v-model="code.codeType" placeholder="请选择代码语言" >
          <el-option
            v-for="item in types"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="代码内容" prop="codeText" required>
        <el-input
            v-model="code.codeText"
            type="textarea"
            autosize
            placeholder="粘贴你的代码到这里"
            :autosize="{ minRows: 8, maxRows: 15 }"
            size="medium"></el-input>
      </el-form-item>
    </el-form>
    <div id="button-group">
      <el-button type="primary" @click="submit">更新</el-button>
      <el-button @click="exit">取消</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "UpdateCodeForm.vue",
  props: {
    codeProp: {
      type: Object
    }
  },

  data() {
    let checkCodeTitle = (rule, value, callback) => {
      if (value) {
        let temp = value.split(" ");
        if (temp.length !== 1) {
          new callback(new Error("代码标题不能包含空格"));
        }
      }
      callback();
    };
    return {
      types: [{
        value: 'java',
        label: 'Java'
      }, {
        value: 'python',
        label: 'Python'
      }, {
        value: 'cpp',
        label: 'C/C++'
      }],
      code: this.codeProp,
      url: '',
      rules: {
        codeText: [
          { required: true, whitespace: true, message: "代码不能为空", trigger: ['change', 'blur'] }
        ],
        codeTitle: [
          { validator: checkCodeTitle, trigger: ['change', 'blur'] }
        ],
      },
    }
  },
  methods: {
    exit(){
      this.$emit("exit");
    },
    load() {
      console.log("codeProp", JSON.stringify(this.code))
      this.cssLoad();
    },
    cssLoad() {
      // 设置代码语言的选择框大小与其他输入框大小相同
      let item1 = document.querySelector(".el-form-item__content");
      let item2 = document.querySelector("#select > .el-form-item__content");
      console.log(item1, item2);
      item2.style.width = window.getComputedStyle(item1, null).width;
    },
    async submit() {
      let flag = true;
      await this.$refs['code'].validate((valid) => {
        console.log(valid);
        flag = valid;
      });
      if (!flag) return false;
      console.log(JSON.stringify(this.code));
      this.$axios({
        method: "post",
        url: `${this.$url}/code/update`,
        data: {
          "codeId": this.code.codeId,
          "codeText": this.code.codeText,
          "codeType": this.code.codeType,
          "codeTitle": this.code.codeTitle,
        }
      }).then((response) => {
        console.log(response);
        if (response.data.flag === true) {
          this.$message({
            message: '更新成功',
            type: 'success',
            duration: 1000
          });
          setTimeout((() => {
            this.$emit('reload');
          }), 1000);
        }
      })
    },
  },
  mounted() {
    this.load();
  }
}
</script>

<style scoped>
/*>>>是CSS的伪类, 穿透组件的作用域限制，使得在子组件中可以修改父组件的样式。*/
>>> .el-form-item__label{
  /*设置行高*/
  line-height: 32px;
  /*字体加粗*/
  font-weight: 900;
}
>>> .el-form-item{
  margin-bottom: 0;
}
#codeTitle {
  margin-bottom: 20px;
  /*width: 100%;*/
}

.el-select {
  margin-bottom: 10px;
}

#upload {
  float: right;
  margin-bottom: 20px;
}

#button-group {
  margin-top: 20px;
  text-align: center;
}
</style>