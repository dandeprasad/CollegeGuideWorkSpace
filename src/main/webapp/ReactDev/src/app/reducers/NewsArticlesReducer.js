const NewsArticlesReducer = (state =
    {
    result:[],
    result1:[]
}  , action)=>{

    switch (action.type){
        case "NEWS_ARTICLES_RESULT":

        state = {

        result: [...state.result,action.payload],
result1: [...state.result1]
        }
        break;
        case "SET_AGE":

          state = {
            ...state,
            age:action.payload
        }
        break;
        case "NEWS_SLIDES_RESULT":

          state = {
            result1: [...state.result1,action.payload],
            result: [...state.result]
        }

        break;

    }
    return state;
};
export default NewsArticlesReducer;
