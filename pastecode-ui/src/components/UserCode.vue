<template>
  <div>
    <div id="heat" :style="{ width: '1000px', height: '200px', left: '25%'}">
  </div>
  <div id="user-info" v-if="show">
    <el-col :span="16" :offset="1">
      <el-card :body-style="{ padding: '0px' }" class="mt-10" >
        <el-tabs type="border-card">
          <el-tab-pane label="分享"><user-share-code :user-id="userId"></user-share-code></el-tab-pane>
          <el-tab-pane label="运行"><user-run-code :user-id="userId"></user-run-code></el-tab-pane>
          <el-tab-pane label="消息" v-if="showMessage"><user-message :user-id="userId"></user-message></el-tab-pane>
          <el-tab-pane label="收藏"><user-collect-code :user-id="userId"></user-collect-code></el-tab-pane>
        </el-tabs>
      </el-card>
    </el-col>
  </div>
  </div>
</template>

<script>
import UserAllCode from "@/components/UserAllCode";
import UserShareCode from "@/components/space/user-code/user-share-code";
import UserManagement from "@/views/admin/UserManagement";
import UserMessage from "@/components/space/user-code/user-message";
import UserCollectCode from "@/components/space/user-code/user-collect-code";
import UserRunCode from "@/components/space/user-code/user-run-code";
import echarts from "echarts";
export default {
  name: "UserCode",
  components: {UserRunCode, UserCollectCode, UserMessage, UserShareCode, UserAllCode},
  data() {
    return {
      showMessage: false,
      userId: '',
      userAccount: '',
      show: false,
      year: new Date().getFullYear(),
    }
  },
  methods: {
    async load() {

      this.userAccount = window.location.pathname.replace("/space/", "");

      await this.$axios({
        method: "get",
        url: `${this.$url}/user/getUserId/${this.userAccount}`
      }).then((response) => {
        this.userId = response.data.data;
        this.showMessage = (this.userId.toString()) === localStorage.getItem("userId");
      });

      await this.$axios({
        method: 'get',
        url: `${this.$url}/code/getUserCodeDate/${this.year}/${this.userId}`,
      }).then((response) => {
            if (response.data.flag) {
              let list = response.data.data;
              this.codeHeatmapInfo = [];
              list.forEach((value) => {
                this.codeHeatmapInfo[value.codeCreateDate] = value.codeCreateDateTotal;
              });

              console.log(this.codeHeatmapInfo)
            }
      });

      await this.heatmap();
    },

    heatmap() {

      const echarts = require('echarts');

      let myChart = echarts.init(document.getElementById('heat'));

      console.log(myChart);

      myChart.setOption({
        title: {
          left: 'center',
          text: `${this.year}年代码提交日历`,
        },
        tooltip: {
          trigger: 'item',
          formatter: function (p) {
            return `提交代码：${p.data[1]}<br>${echarts.format.formatTime('yyyy-MM-dd', p.data[0])}`;
          }
        },
        visualMap: {
          min: 0,
          max: 300,
          type: 'piecewise',
          orient: 'horizontal',
          left: 'center',
          top: 30
        },
        calendar: {
          top: 80,
          cellSize: ['auto', 13],
          range: new Date().getFullYear(),
          itemStyle: {
            borderWidth: 0.5
          },
          yearLabel: { show: false }
        },
        series: {
          type: 'heatmap',
          coordinateSystem: 'calendar',
          data: this.getVirtualData('2023'),
          labelLayout: {
            fontSize: 50,
          },
        }
      });
    },
    getVirtualData(year) {

      const echarts = require('echarts');

      const date = + echarts.time.parse(year + '-01-01');
      const end = + echarts.time.parse(+year + 1 + '-01-01');
      const dayTime = 3600 * 24 * 1000;
      const data = [];
      for (let time = date; time < end; time += dayTime) {

        const t = echarts.time.format(time, '{yyyy}-{MM}-{dd}', false);

        data.push([
          // format 第二个参数代表是否为 UTC 时间
          t, this.codeHeatmapInfo[t] !== 0 ? this.codeHeatmapInfo[t] : 0
        ]);
      }

      console.log(data);

      return data;
    }
  },
  async created() {
    await this.load();
    this.show = true;
  }
}
</script>

<style scoped>
.mt-10 {
  margin-top: 10px;
}
.el-divider--horizontal{
  width: 80% !important;
  margin-left: 10% !important;
  margin-right: 10% !important;
}
.el-divider {
  margin: 8px;
}
b{
  font-size: 18px;
}
</style>