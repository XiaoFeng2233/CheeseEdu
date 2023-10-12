
export default {
    namespaced:true,
    state:{
        token:undefined,
        userName:undefined,
        userId:undefined,
        isAdmin:undefined,
        expireTime:undefined,
        nickName:undefined,
        avatar:undefined
    },
    getters:{

    },
    mutations:{
        logout(states){
            states.avatar = undefined
            states.expireTime = undefined
            states.isAdmin = undefined
            states.token = undefined
            states.nickName = undefined
            states.userName = undefined
            states.userId = undefined
            states.token = undefined
        },
        update(states,newState){
            states.avatar = newState.avatar
            states.expireTime = newState.expireTime
            states.isAdmin = newState.isAdmin
            states.token = newState.token
            states.nickName = newState.nickName
            states.userName = newState.userName
            states.userId = newState.userId
        }
    }
}