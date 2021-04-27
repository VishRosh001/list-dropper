package com.list.dropper.dropbox;

import com.list.dropper.PicassoClient;

public abstract class DropboxAuthActivity extends DropboxActivity{

    @Override
    protected void onResume() {
        super.onResume();

        if(this.isAuth()) {
            initAndLoadData(DropboxUtils.getAccesToken(this));
        }
    }

    private void initAndLoadData(String accessToken) {
        DropboxClientFactory.init(accessToken);
        PicassoClient.init(getApplicationContext(), DropboxClientFactory.getClient());
        loadData();
    }

    protected abstract void loadData();
}
