/**
 * author     :  赵虎
 * direction  :  用于 i18n国际化语言切换时，
 ****************设置 Table表格的表头设置
 */

import {mapState} from 'vuex'

export default {
  created() {
  },
  computed: {
    ...mapState({
      local: state => state.app.local
    })
  },
  watch: {
    local: function (n, o) {
      this.GetTabTitLang()
    }
  },
  mounted() {
    this.$nextTick(() => {
      try {
        this.GetTabTitLang()
        this.tab_H = this.AF.getDom_H(this.tabBox)
      } catch (e) {
      }
    })
  },
  methods: {
    GetTabTitLang() {
      this.tableTiT.forEach((it, index) => {
        if(it.i18n){
          it.title = this.$t(it.i18n)
        }
      })
    }
  }
}
