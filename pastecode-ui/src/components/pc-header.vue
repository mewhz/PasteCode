<template>
  <div id="pc-header">
    <el-menu
        :default-active="$route.path"
        router
        mode="horizontal"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
      <el-menu-item index="/">首页</el-menu-item>
      <el-menu-item index="/list">列表</el-menu-item>
      <el-menu-item index="/run">运行</el-menu-item>
      <el-menu-item index="/diff">比对</el-menu-item>
      <el-dropdown id="item-login" @command="handleCommand" trigger="click">
        <el-menu-item v-show="userName !== null">{{ userName }}</el-menu-item>
        <el-menu-item v-show="userName === null" @click="jumpLogin">登录/注册</el-menu-item>
        <el-dropdown-menu slot="dropdown" v-show="userName !== null">
          <el-dropdown-item command="admin" v-show="userRole === '1'">系统管理</el-dropdown-item>
          <el-dropdown-item command="space">我的空间</el-dropdown-item>
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "pcHeader",
  data() {
    return{
      activeIndex: "/",
      userName: localStorage.getItem("userName"),
      userAccount: localStorage.getItem("userAccount"),
      userRole: localStorage.getItem("userRole"),
    }
  },
  methods: {

    // 跳转到登录界面
    jumpLogin() {
      this.$router.push('/login');
    },


    //处理下拉框单击事件
    handleCommand(command) {
      if (command === "logout") this.logout();
      else if (command === "space") this.space();
      else if (command === "admin") this.admin();
    },
    // 退出登录
    logout() {
      this.$axios('http://127.0.0.1:9090/user/logout/', {
        headers: this.getHeaders(),
      }).then(() => {

        this.$message({
          message: '退出登录成功！！',
          type: 'success',
          duration: 800
        });
        localStorage.clear();

        window.location.reload();

      })
    },

    // 进入个人空间
    space() {
      this.$router.push("/space/" + this.userAccount);
    },
    admin() {
      this.$router.push("/admin");
    }
  }
}
</script>

<style scoped>
/*
  text-align 的默认值是 left
  利用该属性破坏 父组件的 text-align: center 属性
*/
#pc-header {
  text-align: left;
}
.el-menu-item {
  left: 20%;
  font-size: 16px;
}
#item-login {
  left: 72%;
}
.el-dropdown-menu {
  left: 92% !important;
}
</style>