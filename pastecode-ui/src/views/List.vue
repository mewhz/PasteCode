<template>
  <div id="list">
  <pc-header></pc-header>
  <div class="list"
       v-infinite-scroll="infinite"
       :infinite-scroll-disabled="disabled">
    <el-input
        placeholder="你想查询什么代码？"
        clearable
        v-model="search"
        @input="selectCode"
        style="width: 80%; margin-left: 10%"
    ></el-input>
    <el-card v-for="code in codeList" style="margin-bottom: 50px" :key="code.codeId">
      <el-link id="code-title" :underline="false" @click="goCode(code.codeId)">
        {{ code.codeTitle }}
      </el-link>
<!--        <span id="code-title"><b>{{ code.codeTitle }}</b></span>-->
      <pc-code :code="code"></pc-code>
    </el-card>
    <p v-if="loading">加载中...</p>
    <p v-if="noMore">没有更多了</p>
  </div>
  <el-backtop target=".list"></el-backtop>
  </div>
</template>

<script>
import PcHeader from "@/components/pc-header";
import PcCode from "@/components/pc-code";
export default {
  name: "List",
  components: {PcCode, PcHeader},
  data() {
    return {
      total: 0,
      codeList: [],
      current: 1,
      size: 5,
      search: '',
      oldSearch: '',
      loading: false,
    }
  },
  computed: {
    noMore () {
      console.log(this.current * this.size, this.total)
      return (this.current * this.size) >= this.total
    },
    disabled () {
      return this.loading || this.noMore
    }

  },
  methods : {

    selectCode(codeTitle) {
      this.load();
    },


    infinite() {
      // this.load();
      this.current ++;
      this.loading = true;
      setTimeout(() => {
        this.load();
        this.loading = false;
      }, 800);
      console.log(new Date().getTime());
    },

    load() {

      if (this.search === '' && this.oldSearch !== '') {
        this.codeList = [];
        this.current = 1;
        console.log("清空1")
      }
      if (this.search !== this.oldSearch) {
        this.current = 1;
        this.codeList = [];
        console.log("清空2");
      }

      this.$axios({
        url: `${this.$url}/code/pageList`,
        method: 'get',
        params: {
          "codeTitle": this.search,
          "current": this.current - 1,
        }
      }).then((response) => {
        this.codeList = this.codeList.concat(response.data.data.records);
        this.total = response.data.data.count;
        this.oldSearch = this.search;
      });
    },
    goCode(id) {
      this.$router.push('/code/id/' + id);
    }
  },
  created() {
    this.load();
  }
}
</script>

<style scoped>
>>> .el-card__body {
  padding: 0;
}
.list {
  height: 90vh;
  overflow: auto;
}

.infinite-list .infinite-list-item {
  /* 设置弹性布局 */
  display: flex;
  /* 垂直居中 */
  align-items: center;
  /* 水平居中 */
  justify-content: center;
  height: 50px;
  background: #e8f3fe;
  margin: 10px;
  color: #e43333
}
.el-card {
  margin-bottom: 0 !important;
  width:80%;
  margin-left: 10%;
}
#code-title {
  text-align: center;
  font-size: 25px;
}
p {
  text-align: center;
  font-size: 25px;
}
.el-input {
  margin: 5px;
  width: 99%;
}
</style>