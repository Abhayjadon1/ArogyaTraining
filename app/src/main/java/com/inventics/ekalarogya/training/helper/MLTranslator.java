package com.inventics.ekalarogya.training.helper;

//import com.google.mlkit.common.model.DownloadConditions;
//import com.google.mlkit.nl.translate.TranslateLanguage;
//import com.google.mlkit.nl.translate.Translation;
//import com.google.mlkit.nl.translate.Translator;
//import com.google.mlkit.nl.translate.TranslatorOptions;


public class MLTranslator {
    String txt = null;
//    Translator englishHindiTranslator;
//    TranslatorOptions options =
//            new TranslatorOptions.Builder()
//                    .setSourceLanguage(TranslateLanguage.ENGLISH)
//                    .setTargetLanguage(TranslateLanguage.HINDI)
//                    .build();
//
//
//
//    public String trans(Context mContaxt, String string){
//
//        Resources res = mContaxt.getResources();
//        Configuration conf = res.getConfiguration();
//        Locale savedLocale = conf.locale;
//        conf.locale = new Locale("en"); // whatever you want here
//        res.updateConfiguration(conf, null);
//        SharedPreferences sharedPreferences = mContaxt.getSharedPreferences(Constant.LOGIN_SHARED, Context.MODE_PRIVATE);
//        conf.locale = new Locale(sharedPreferences.getString(LOCALE, LocaleManager.DEFAULT_LOCALE));
//        englishHindiTranslator =
//                Translation.getClient(options);
//
//        DownloadConditions conditions = new DownloadConditions.Builder()
//                .requireWifi()
//                .build();
//        englishHindiTranslator.downloadModelIfNeeded(conditions)
//                .addOnSuccessListener(
//                        new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void v) {
//                                // Model downloaded successfully. Okay to start translating.
//                                Log.d("TAG", "onSuccess: DownloadConditions ");
//                            }
//                        })
//                .addOnFailureListener(
//                        new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Model couldn’t be downloaded or other internal error.
//                                Log.d("TAG", "onFailure: DownloadConditions "+e.getMessage());;
//                            }
//                        });
//
//        englishHindiTranslator.translate("Home")
//                .addOnSuccessListener(
//                        new OnSuccessListener<String>() {
//                            @Override
//                            public void onSuccess(@NonNull String translatedText) {
//                                // Translation successful.
//                                txt=(translatedText);
//                                Log.d("TAG", "Done: "+translatedText);
//                            }
//                        })
//                .addOnFailureListener(
//                        new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Error.
//                                // ...
////
//                                Log.d("TAG", "onFailure: "+e.getMessage());
//                            }
//                        });
//
//
//        return txt;
//    }
//
//    void translate_hin(String text) {
//
//    }


}
