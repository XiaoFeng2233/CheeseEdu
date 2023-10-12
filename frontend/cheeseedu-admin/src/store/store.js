import {createStore,createLogger} from "vuex";
import user from "./modules/user.js";
const store = createStore({
    namespaced:true,
    modules:{
        user
    },
    strict:true,

})
export default store