<template>
  <div class="tabLayout" ref="tabLayout" @click="verifyChild($event)">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "LeftLayout",
  props: {
    tabs: null
  },
  data() {
    return {
      current: 1,
    }
  },
  methods: {
    verifyChild(e) {
      let tabLayout = this.$refs.tabLayout;
      for (let i = 0; i < tabLayout.children.length; i++) {
        let child = tabLayout.children[i];
        if (child.contains(e.target)) {
          this.toggleTab(i, child)
        }
      }
    },
    toggleTab(index, node) {
      if (this.$route.path!==this.tabs[index].url){
        this.$router.replace(this.tabs[index].url)
      }
      this.$emit('changeTab', index)
      // let tabLayout = this.$refs.tabLayout;
      // let winWidth = window.innerWidth;
      // if (node) {
      //   let liLeft = node.offsetLeft,
      //       liWidth = node.offsetWidth,
      //       liCenter = (winWidth - liWidth) / 2,
      //       liTarget = liLeft - liCenter;
      //
      //   tabLayout.scrollTo({left: liTarget, top: 0, behavior: 'smooth'});
      //   // tabLayout.scrollLeft = liTarget;
      //
      // }
    },
  },
  created() {
    for (let i = 0; i < this.tabs.length; i++) {
      if (this.$route.path ===  this.tabs[i].url) {
        this.$emit('changeTab', i)
      }
    }
  }
};
</script>
<style scoped>

.tabLayout {
  position: fixed;
  bottom: 0;
  top: 15vw;
  left: 0;
  width: 15vw;
  z-index: 999;
  background: white;
  height: 100%;
  overflow-y: scroll;
  border-right: solid 1px #efefef;
}

.tabLayout::-webkit-scrollbar {
  display: none;
}

.tabLayout > * {
  height: 44px;
  width: 100%;
  line-height: 44px;
  text-align: center;
}


</style>
