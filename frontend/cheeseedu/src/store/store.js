import {createStore,createLogger} from "vuex";
import user from "./modules/user.js";
import siteconfig from "./modules/siteconfig.js";
const store = createStore({
    namespaced:true,
    modules:{
        user,
        siteconfig
    },
    strict:true,

})
export default store