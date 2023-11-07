const EventsDetailsReducer = (state =
    {
    result:[],
    links:[]
}  , action)=>{

    switch (action.type){
        case "EVENTS_DETAILS_OVERVIEW":

        state = {
...state,
        result: [action.payload]

        }
        break;
        case "EVENTS_DETAILS_LINKS":

        state = {
...state,
        links: [action.payload]

        }
        break;
    }
    return state;
};
export default EventsDetailsReducer;
