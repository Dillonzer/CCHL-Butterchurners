package com.personal.dillon.butterchurners;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * Created by Dillon on 2016-03-02
 */
public class SchedFragment extends Fragment {
    ProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        return view;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //CALL CLASS
        new JsoupRun().execute();
    }



    private class JsoupRun extends AsyncTask<Void, Void, Void> {
        String url = "http://stats.adultrechockey.ca/c-regina/ashnregina/en/stats/schedule.php?view=season&team_id=3642&division_id=-1&season_id=70";
        Elements el_game_dates;
        Elements el_visiting_scores;
        Elements el_visiting_teams;
        Elements el_home_scores;
        Elements el_home_teams;
        Elements el_venue_or_scores;
        Elements el_arena;
        boolean nextgameFound = false;
        int num_of_games = 0;
        int next_game_index = 0;
        int game_counter = 0;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("Schedule");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document doc = Jsoup.connect(url).get();

                //Time to get the Elements
                el_game_dates = doc.getElementsByClass("ls-date");
                el_visiting_scores = doc.getElementsByClass("ls-visiting-score");
                el_visiting_teams = doc.getElementsByClass("ls-visiting");
                el_home_scores = doc.getElementsByClass("ls-home-score");
                el_home_teams = doc.getElementsByClass("ls-home");
                el_venue_or_scores = doc.getElementsByClass("ls-venue");
                el_arena = doc.getElementsByClass("ls-gamelinks");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // CODE GOES HERE

            //next game variables
            TextView nextgame_visteam = (TextView) getView().findViewById(R.id.tv_visteam_schedule_nextgame);
            TextView nextgame_hometeam = (TextView) getView().findViewById(R.id.tv_hometeam_schedule_nextgame);
            TextView nextgame_VS = (TextView) getView().findViewById(R.id.tv_VS_schedule_nextgame);
            TextView nextgame_date = (TextView) getView().findViewById(R.id.tv_date_schedule_nextgame);
            TextView nextgame_time = (TextView) getView().findViewById(R.id.tv_time_schedule_nextgame);
            TextView nextgame_arena = (TextView) getView().findViewById(R.id.tv_arena_schedule_nextgame);

            String s_nextgame_visteam="";
            String s_nextgame_hometeam="";
            String s_nextgame_VS="";
            String s_nextgame_date="";
            String s_nextgame_time="";
            String s_nextgame_arena="";

            //upcoming game variables
            TextView upcoming_visteam = (TextView) getView().findViewById(R.id.tv_visteam_schedule_upcoming);
            TextView upcoming_hometeam = (TextView) getView().findViewById(R.id.tv_hometeam_schedule_upcoming);
            TextView upcoming_VS = (TextView) getView().findViewById(R.id.tv_VS_schedule_upcoming);
            TextView upcoming_date = (TextView) getView().findViewById(R.id.tv_date_schedule_upcoming);
            TextView upcoming_time = (TextView) getView().findViewById(R.id.tv_time_schedule_upcoming);
            TextView upcoming_arena = (TextView) getView().findViewById(R.id.tv_arena_schedule_upcoming);

            String s_upcoming_visteam="";
            String s_upcoming_hometeam="";
            String s_upcoming_VS="";
            String s_upcoming_date="";
            String s_upcoming_time="";
            String s_upcoming_arena="";

            //previous game variables
            TextView previous_visteam = (TextView) getView().findViewById(R.id.tv_visteam_schedule_previous);
            TextView previous_hometeam = (TextView) getView().findViewById(R.id.tv_hometeam_schedule_previous);
            TextView previous_VS = (TextView) getView().findViewById(R.id.tv_VS_schedule_previous);
            TextView previous_date = (TextView) getView().findViewById(R.id.tv_date_schedule_previous);
            TextView previous_visscore = (TextView) getView().findViewById(R.id.tv_visscore_schedule_previous);
            TextView previous_homescore = (TextView) getView().findViewById(R.id.tv_homescore_schedule_previous);
            TextView previous_final = (TextView) getView().findViewById(R.id.tv_final_schedule_previous);

            String s_previous_visteam ="";
            String s_previous_hometeam="";
            String s_previous_VS="";
            String s_previous_date="";
            String s_previous_visscore="";
            String s_previous_homescore="";
            String s_previous_final="";

            //Goes through the arenas, stops counting when it find when the next game is and when the last game is
            for(Element test : el_venue_or_scores)
            {
                num_of_games++;

                if((test.text().equals("Final") || test.text().equals("Final OT")) && !nextgameFound)
                {
                    next_game_index++;
                } else
                {
                    nextgameFound = true;
                }
            }

            //start getting the strings together
            for(Element gd : el_game_dates)
            {
                if (game_counter < next_game_index) //previous
                {
                    s_previous_date += gd.text() + "\n";
                } else if (game_counter == next_game_index) //next game
                {
                    s_nextgame_date = gd.text();
                } else //upcoming
                {
                    s_upcoming_date += gd.text() + "\n";
                }

                game_counter++;
            }

            game_counter = 0;

            for(Element vs : el_visiting_scores)
            {
                if (game_counter < next_game_index) //previous
                {
                    s_previous_visscore += vs.text() + "\n";
                }

                game_counter++;
            }

            game_counter = 0;
            for(Element vt : el_visiting_teams)
            {
                if (game_counter < next_game_index) //previous
                {
                    s_previous_visteam += vt.text() + "\n";
                } else if (game_counter == next_game_index) //next game
                {
                    s_nextgame_visteam = vt.text();
                } else //upcoming
                {
                    s_upcoming_visteam += vt.text() + "\n";
                }

                game_counter++;
            }

            game_counter = 0;

            for(Element hs : el_home_scores)
            {
                if (game_counter < next_game_index) //previous
                {
                    s_previous_homescore += hs.text() + "\n";
                }

                game_counter++;
            }

            game_counter = 0;

            for(Element ht : el_home_teams)
            {
                if (game_counter < next_game_index) //previous
                {
                    s_previous_hometeam += ht.text() + "\n";
                } else if (game_counter == next_game_index) //next game
                {
                    s_nextgame_hometeam = ht.text();
                } else //upcoming
                {
                    s_upcoming_hometeam += ht.text() + "\n";
                }

                game_counter++;
            }

            game_counter = 0;

            for(Element vos: el_venue_or_scores)
            {
                if (game_counter < next_game_index) //previous
                {
                    s_previous_final += vos.text() + "\n";
                } else if (game_counter == next_game_index) //next game
                {
                    s_nextgame_time = vos.text();
                } else //upcoming
                {
                    s_upcoming_time += vos.text() + "\n";
                }

                game_counter++;
            }

            game_counter = 0;

            for(Element a: el_arena)
            {
                if (game_counter > next_game_index)  //upcoming
                {
                    s_upcoming_arena += a.text() + "\n";
                } else if (game_counter == next_game_index) //next game
                {
                    s_nextgame_arena = a.text();
                }

                game_counter++;
            }

            game_counter = 0;

            for(int i = 0; i < num_of_games; i++)
            {

                if (i < next_game_index) //previous
                {
                    s_previous_VS += "VS\n";
                } else if (i == next_game_index) //next game
                {
                    s_nextgame_VS += " VS ";
                } else //upcoming
                {
                    s_upcoming_VS += "VS\n";
                }
            }

            //set textviews

            nextgame_visteam.setText(s_nextgame_visteam);
            nextgame_VS.setText(s_nextgame_VS);
            nextgame_hometeam.setText(s_nextgame_hometeam);
            nextgame_date.setText(s_nextgame_date);
            nextgame_time.setText(s_nextgame_time);
            nextgame_arena.setText(s_nextgame_arena);

            previous_date.setText(s_previous_date);
            previous_final.setText(s_previous_final);
            previous_homescore.setText(s_previous_homescore);
            previous_hometeam.setText(s_previous_hometeam);
            previous_visscore.setText(s_previous_visscore);
            previous_visteam.setText(s_previous_visteam);
            previous_VS.setText(s_previous_VS);

            upcoming_arena.setText(s_upcoming_arena);
            upcoming_date.setText(s_upcoming_date);
            upcoming_hometeam.setText(s_upcoming_hometeam);
            upcoming_time.setText(s_upcoming_hometeam);
            upcoming_time.setText(s_upcoming_time);
            upcoming_visteam.setText(s_upcoming_visteam);
            upcoming_VS.setText(s_upcoming_VS);

            mProgressDialog.dismiss();
        }
    }
}


