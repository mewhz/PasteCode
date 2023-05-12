<template>
  <code-diff
      :old-string="oldString"
      :new-string="newString"
      language="python"
      output-format="side-by-side"/>
</template>

<script>

export default {
  name: 'About',
  data(){
    return {
      oldString: "print('Hello')",
      newString: "print('Hellso')"
    }
  },
  created() {

  },
  methods:{

  }
}
</script>

<style>

</style>
