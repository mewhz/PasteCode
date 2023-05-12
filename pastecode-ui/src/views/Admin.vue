<template>
  <div id="about">
    <el-container>
      <el-aside width="200px">
        <el-menu
            style="height: 100vh"
            :default-active="this.$route.path"
            router
            text-color="#fff"
            background-color="#304156"
            active-text-color="#409EFF">
          <el-menu-item index="/admin">
            <template slot="title"><i class="el-icon-s-home"></i>首页</template>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-s-tools"></i>系统管理</template>
            <el-menu-item index="/admin/userManagement">
              <i class="el-icon-user-solid"></i>用户管理
            </el-menu-item>
            <el-menu-item index="/admin/codeManagement">
              <i class="el-icon-s-opportunity"></i>代码管理
            </el-menu-item>
            <el-menu-item index="/admin/runManagement">
              <i class="el-icon-s-claim"></i>运行管理
            </el-menu-item>
            <el-menu-item index="/admin/logManagement">
              <i class="el-icon-s-order"></i>日志管理
            </el-menu-item>
          </el-submenu>
          <el-menu-item  index="/">
            <template slot="title"><i class="el-icon-s-promotion"></i>返回前台</template>
          </el-menu-item>
          <el-menu-item @click="logout" index="/">
            <template slot="title"><i class="el-icon-s-platform"></i>退出登录</template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="el-main"><router-view /></el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: '首页'
    }
  },
  methods: {
    logout() {
      localStorage.clear();

      window.location.reload();
    }
  }
};
</script>

<style scoped>
/* 设置铺满整个屏幕的高度 */
.el-main {
  height: 100vh;
}
</style>