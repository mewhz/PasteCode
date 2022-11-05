<template>
  <div>
    <h1>{{ msg }}</h1>
    <el-button @click="button">button</el-button>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    value: {
      type: String,
      default: "Hello",
    }
  },
  data() {
    return {
      msg: this.value
    }
  },
  methods: {
    button() {
      console.log(this.msg);
      this.msg = "abc";
    }
  },
}
</script>
