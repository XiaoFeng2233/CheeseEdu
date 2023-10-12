export default {
    namespaced:true,
    state:{
        siteName:undefined,
        siteIcon:undefined,
        siteFooter:undefined,
        siteDescription:undefined,
        siteLogo:undefined,
        siteKeyword:undefined,
    },
    mutations:{
        update(states,newState){
            states.siteName = newState.siteName
            states.siteIcon = newState.siteIcon
            states.siteFooter = newState.siteFooter
            states.siteDescription = newState.siteDescription
            states.siteLogo = newState.siteLogo
            states.siteKeyword = newState.siteKeyword
        }
    }
}