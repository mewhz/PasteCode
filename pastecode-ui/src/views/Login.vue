<template>
  <div id="login">
    <el-col :span="8" :offset="8">
      <h1>用户登录</h1>
      <el-form :model="user" :rules="rules" ref="ruleForm">
        <el-form-item prop="account">
          <el-input type="text" v-model="user.account" autocomplete="off" placeholder="账号"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="user.password" autocomplete="off" placeholder="密码"/>
        </el-form-item>
      </el-form>
      <el-button @click="submit">登录</el-button>
    </el-col>
  </div>
</template>

<script>

export default {
  name: 'Login',
  data() {
    return {
      user: {
        account: '',
        password: '',
      },
      rules: {
        account: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
      },
    }
  },
  methods: {
    // 提交
    async submit() {
      let flag = true;
      await this.$refs['ruleForm'].validate((valid) => {
        if (!valid) {
          flag = false;
        }
      });


      if (!flag) return;
      // console.log(this.user)
      this.$axios({
        method: "post",
        url: 'http://127.0.0.1:9090/user/login/',
        data: {
          "userAccount": this.user.account,
          "userPassword": this.user.password
        },
      }).then((response) => {

        console.table(response.data);

        let data = response.data;

        if (0 === data.responseCode) {

          let tokenValue = data.responseData.tokenValue;
          let tokenName  = data.responseData.tokenName;

          localStorage.setItem("tokenValue", tokenValue);
          localStorage.setItem("tokenName", tokenName);


          // 弹出的信息提示
          this.$message({
            message: '登录成功！！',
            type: 'success',
            duration: 500
          });

          setTimeout(() => {
            this.userJump();
          }, 500);

        } else {
          this.$message({
            message: data.responseMessage,
            type: 'warning',
            duration: 1000
          });
        }
      });
    },

    // 跳转
    async userJump() {
      // localStorage.setItem("userStatus", this.user.status);
      // localStorage.setItem("uid", this.user.username);
      console.log("this.path:", localStorage.getItem("path"));
      let path = localStorage.getItem("path");
      if (path === null) {
        await this.$router.push('/');
      }
      else {
        await this.$router.push(path);
      }
    }
  }
}
</script>
<style>
</style>
