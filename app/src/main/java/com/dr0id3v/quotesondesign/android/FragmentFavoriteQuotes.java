package com.dr0id3v.quotesondesign.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dr0id3v.quotesondesign.R;

public class FragmentFavoriteQuotes extends Fragment
{
  private RvAdapterFavoriteQuotes rvFavoriteQuotesAdapter;

  @Nullable
  @Override
  public View onCreateView( LayoutInflater inflater,
                            @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState )
  {
    return inflater.inflate( R.layout.fragment_favorite_quotes, container, false );
  }

  @Override
  public void onViewCreated( View view, @Nullable Bundle savedInstanceState )
  {
    super.onViewCreated( view, savedInstanceState );
    RecyclerView rvFavoriteQuotes = view.findViewById( R.id.rvFavoriteQuotes );

    App app = (App) getActivity().getApplication();
    rvFavoriteQuotesAdapter = new RvAdapterFavoriteQuotes( app.getQuoteRepository() );
    rvFavoriteQuotes.setAdapter( rvFavoriteQuotesAdapter );
    rvFavoriteQuotes.setLayoutManager( new LinearLayoutManager( getContext() ) );
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
    rvFavoriteQuotesAdapter.destroy();
  }
}
