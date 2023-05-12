<template>
  <div id="login">
    <el-col :offset="8" :span="9">
      <h1 style="text-align: center">用户登录</h1>
      <el-form :model="user" :rules="rules" ref="ruleForm">
        <el-form-item prop="account">
          <el-input type="text" v-model="user.account" autocomplete="off" placeholder="账号"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="user.password" autocomplete="off" placeholder="密码"/>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button @click="submit">登录</el-button>
        <el-button @click="register">注册</el-button>
        <el-button @click="testLogin1">星落登录</el-button>
        <el-button @click="testLogin2">mewhz登录</el-button>
        <el-button @click="randomLogin">{{ randomAccount }} 登录</el-button>
      </div>
    </el-col>
  </div>
</template>

<script>

export default {
  name: 'Login',
  data() {
    return {
      randomAccount: '',
      user: {
        account: '',
        password: '',
      },
      userRole: 0,
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
        url: `${this.$url}/user/login/`,
        data: {
          "userAccount": this.user.account,
          "userPassword": this.user.password
        },
      }).then((response) => {
        let resp = response.data;
        if (20000 === resp.code) {

          // 获取响应数据中的数据字段
          resp = resp.data;

          localStorage.setItem("userId", resp.userId);
          localStorage.setItem("userAccount", resp.userAccount);
          localStorage.setItem("userName", resp.userName);
          localStorage.setItem("userRole", resp.userRole);

          this.userRole = resp.userRole;

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
            message: resp.message,
            type: 'warning',
            duration: 1000
          });
        }
      });
    },

    // 跳转
    async userJump() {
      let path = localStorage.getItem("path");
      if (path === null) {
        await this.$router.push('/');
      }
      else {
        await this.$router.push(path);
      }
    },

    // 注册
    register() {
      this.$router.push("/register");
    },

    // 测试专用登录账号
    testLogin1() {
      this.user.account = "12345678";
      this.user.password = "123456";
      this.submit();
    },
    testLogin2() {
      this.user.account = "97972262";
      this.user.password = "123456";
      this.submit();
    },

    randomLogin() {
      this.user.account = this.randomAccount;
      this.user.password = '123456';
      this.submit();
    },

    load() {
      this.$axios({
        method: "Get",
        url: `${this.$url}/user/random`
      }).then((response) => {
        this.randomAccount = response.data.data;
      })
    }
  },

  mounted() {
    this.load();
    document.addEventListener('keyup', (e) => {
      if (e.key === "Enter") {
        this.submit();
      }
    });
  }

}
</script>
<style>
</style>
