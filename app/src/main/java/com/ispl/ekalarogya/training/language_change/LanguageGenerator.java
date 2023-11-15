package com.ispl.ekalarogya.training.language_change;

import com.ispl.ekalarogya.training.R;

import java.util.ArrayList;
import java.util.List;

public class LanguageGenerator {
    public static List<LanguageModel> getListData(){
        List<LanguageModel> mList = new ArrayList<>();
        if(mList.size()>0)
            mList.clear();
        mList.add(new LanguageModel(R.drawable.english,"English","en",false));
        mList.add(new LanguageModel(R.drawable.hindi,"Hindi","hi",false));
        mList.add(new LanguageModel(R.drawable.kannada,"ಕನ್ನಡ","kn",false));
//        mList.add(new LanguageModel(R.drawable.tamil,"தமிழ்",false));
        mList.add(new LanguageModel(R.drawable.gujarati,"ગુજરાતી","gu",false));
//        mList.add(new LanguageModel(R.drawable.telugu,"తెలుగు",false));
//        mList.add(new LanguageModel(R.drawable.urdu,"اردو",false));
        mList.add(new LanguageModel(R.drawable.bangla,"বাংলা","bn",false));
        mList.add(new LanguageModel(R.drawable.assami,"ভাষা","as",false));
        mList.add(new LanguageModel(R.drawable.oriya,"ଓଡିଆ","or",false));
        return mList;
    }
}
