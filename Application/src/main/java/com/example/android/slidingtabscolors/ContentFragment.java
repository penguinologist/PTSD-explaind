/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.slidingtabscolors;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Simple Fragment used to display some meaningful content for each page in the sample's
 * {@link android.support.v4.view.ViewPager}.
 */
public class ContentFragment extends Fragment {

    private static final String KEY_TITLE = "title";
    private static final String KEY_INDICATOR_COLOR = "indicator_color";
    private static final String KEY_DIVIDER_COLOR = "divider_color";
    private static final String VISIBILITY = "vis";

    /**
     * @return a new instance of {@link ContentFragment}, adding the parameters into a bundle and
     * setting them as arguments.
     */
    public static ContentFragment newInstance(CharSequence title, int indicatorColor,
                                              int dividerColor, boolean visibility) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
        bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
        bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);
        bundle.putBoolean(VISIBILITY, visibility);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager_item, container, false);
    }

    TextView content;
    ArrayList<String> dropdownMenu;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText("Title: " + args.getCharSequence(KEY_TITLE));
            title.setVisibility(View.INVISIBLE);

            int indicatorColor = args.getInt(KEY_INDICATOR_COLOR);


            int dividerColor = args.getInt(KEY_DIVIDER_COLOR);


            //variable declaration

            Button period = (Button) view.findViewById(R.id.button);
            Button clinics = (Button) view.findViewById(R.id.button2);
            Button help = (Button) view.findViewById(R.id.button3);
            Spinner spin = (Spinner) view.findViewById(R.id.spinner);
            content = (TextView) view.findViewById(R.id.textView);
            content.setMovementMethod(new ScrollingMovementMethod());
            TextView about = (TextView) view.findViewById(R.id.about);

            //disabling them all!!!!
            period.setVisibility(View.INVISIBLE);
            clinics.setVisibility(View.INVISIBLE);
            help.setVisibility(View.INVISIBLE);
            spin.setVisibility(View.INVISIBLE);
            about.setVisibility(View.INVISIBLE);


            dropdownMenu = new ArrayList<>();
            dropdownMenu.add(Html.fromHtml("&#8659;")+" Pick an option");
            dropdownMenu.add(Html.fromHtml("&#8659;")+" What is Post-traumatic Stress Disorder (PTSD)?");
            dropdownMenu.add(Html.fromHtml("&#8659;")+" Causes");
            dropdownMenu.add(Html.fromHtml("&#8659;")+" Signs & Symptoms");
            dropdownMenu.add(Html.fromHtml("&#8659;")+" Who is at risk?");
            dropdownMenu.add(Html.fromHtml("&#8659;")+" Diagnosis");
            dropdownMenu.add(Html.fromHtml("&#8659;")+" Treatments");
            dropdownMenu.add(Html.fromHtml("&#8659;")+" Living With");
            dropdownMenu.add(Html.fromHtml("&#8659;")+" Clinical Trials");

            //set the adapter and link the spinner to it
            ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                    android.R.layout.simple_spinner_item, dropdownMenu);
            spin.setAdapter(adapter);


            about.setText("PTSD is a serious issue. Around 8% of the US population has PTSD at any given time. Due to the lack of information and the inherent need to explain what it is to those with doubts, this app was created. If you have any questions, comments, or thoughts about the app, feel free to contact me.");

            //set visibility of objects on the screen
            if (title.getText().toString().contains("Information")) {
                period.setVisibility(View.INVISIBLE);
                clinics.setVisibility(View.INVISIBLE);
                help.setVisibility(View.INVISIBLE);
                spin.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
                about.setVisibility(View.INVISIBLE);

            }

            if (title.getText().toString().contains("Tools")) {
                //TODO initialize the right variables
                content.setVisibility(View.INVISIBLE);
                period.setVisibility(View.INVISIBLE);
                clinics.setVisibility(View.VISIBLE);
                clinics.bringToFront();
                help.setVisibility(View.VISIBLE);
                help.bringToFront();
                about.setVisibility(View.INVISIBLE);
            }

            if (title.getText().toString().contains("About")) {
                //TODO initialize variables
                content.setVisibility(View.INVISIBLE);
                period.setVisibility(View.INVISIBLE);
                clinics.setVisibility(View.INVISIBLE);
                help.setVisibility(View.INVISIBLE);
                spin.setVisibility(View.INVISIBLE);
                about.setVisibility(View.VISIBLE);


            }

            clinics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(view.getContext(),MapsActivity.class));
                }
            });

            help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:99119"));
                    startActivity(callIntent);
                }
            });



            //set listener
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


//                        Toast.makeText(view.getContext(), "index selected: " + i, Toast.LENGTH_SHORT).show();

                    switch (i) {
                        case 1:
                            content.setText("");
                            content.setText("When in danger, it’s natural to feel afraid. This fear triggers many split-second changes in the body to prepare to defend against the danger or to avoid it. This “fight-or-flight” response is a healthy reaction meant to protect a person from harm. But in post-traumatic stress disorder (PTSD), this reaction is changed or damaged. People who have PTSD may feel stressed or frightened even when they’re no longer in danger.\n" +
                                    "\n" +
                                    "PTSD develops after a terrifying ordeal that involved physical harm or the threat of physical harm. The person who develops PTSD may have been the one who was harmed, the harm may have happened to a loved one, or the person may have witnessed a harmful event that happened to loved ones or strangers.\n" +
                                    "\n" +
                                    "PTSD was first brought to public attention in relation to war veterans, but it can result from a variety of traumatic incidents, such as mugging, rape, torture, being kidnapped or held captive, child abuse, car accidents, train wrecks, plane crashes, bombings, or natural disasters such as floods or earthquakes.");
                            break;
                        case 2:
                            content.setText("");
                            content.setText("Genes. Currently, many scientists are focusing on genes that play a role in creating fear memories. Understanding how fear memories are created may help to refine or find new interventions for reducing the symptoms of PTSD. For example, PTSD researchers have pinpointed genes that make:\n" +
                                    "\n" +
                                    "Stathmin, a protein needed to form fear memories. In one study, mice that did not make stathmin were less likely than normal mice to “freeze,” a natural, protective response to danger, after being exposed to a fearful experience. They also showed less innate fear by exploring open spaces more willingly than normal mice.\n" +
                                    "\n" +
                                    "GRP (gastrin-releasing peptide), a signaling chemical in the brain released during emotional events. In mice, GRP seems to help control the fear response, and lack of GRP may lead to the creation of greater and more lasting memories of fear.\n" +
                                    "\n" +
                                    "Researchers have also found a version of the 5-HTTLPR gene, which controls levels of serotonin — a brain chemical related to mood-that appears to fuel the fear response. Like other mental disorders, it is likely that many genes with small effects are at work in PTSD.\n" +
                                    "\n" +
                                    "Brain Areas. Studying parts of the brain involved in dealing with fear and stress also helps researchers to better understand possible causes of PTSD. One such brain structure is the amygdala, known for its role in emotion, learning, and memory. The amygdala appears to be active in fear acquisition, or learning to fear an event (such as touching a hot stove), as well as in the early stages of fear extinction, or learning not to fear.\n" +
                                    "\n" +
                                    "Storing extinction memories and dampening the original fear response appears to involve the prefrontal cortex (PFC) area of the brain, involved in tasks such as decision-making, problem-solving, and judgment. Certain areas of the PFC play slightly different roles. For example, when it deems a source of stress controllable, the medial PFC suppresses the amygdala an alarm center deep in the brainstem and controls the stress response.5The ventromedial PFC helps sustain long-term extinction of fearful memories, and the size of this brain area may affect its ability to do so.\n" +
                                    "\n" +
                                    "Individual differences in these genes or brain areas may only set the stage for PTSD without actually causing symptoms. Environmental factors, such as childhood trauma, head injury, or a history of mental illness, may further increase a person's risk by affecting the early growth of the brain. Also, personality and cognitive factors, such as optimism and the tendency to view challenges in a positive or negative way, as well as social factors, such as the availability and use of social support, appear to influence how people adjust to trauma. More research may show what combinations of these or perhaps other factors could be used someday to predict who will develop PTSD following a traumatic event.\n" +
                                    "\n" +
                                    "The Next Steps for PTSD Research\n" +
                                    "\n" +
                                    "In the last decade, rapid progress in research on the mental and biological foundations of PTSD has lead scientists to focus on prevention as a realistic and important goal.\n" +
                                    "\n" +
                                    "For example, NIMH-funded researchers are exploring new and orphan medications thought to target underlying causes of PTSD in an effort to prevent the disorder. Other research is attempting to enhance cognitive, personality, and social protective factors and to minimize risk factors to ward off full-blown PTSD after trauma. Still other research is attempting to identify what factors determine whether someone with PTSD will respond well to one type of intervention or another, aiming to develop more personalized, effective and efficient treatments.\n" +
                                    "\n" +
                                    "As gene research and brain imaging technologies continue to improve, scientists are more likely to be able to pinpoint when and where in the brain PTSD begins. This understanding may then lead to better targeted treatments to suit each person's own needs or even prevent the disorder before it causes harm.");
                            break;
                        case 3:
                            content.setText("");
                            content.setText("PTSD can cause many symptoms. These symptoms can be grouped into three categories:\n" +
                                    "\n" +
                                    "1. Re-experiencing symptoms\n" +
                                    "\n" +
                                    "Flashbacks—reliving the trauma over and over, including physical symptoms like a racing heart or sweating\n" +
                                    "Bad dreams\n" +
                                    "Frightening thoughts.\n" +
                                    "Re-experiencing symptoms may cause problems in a person’s everyday routine. They can start from the person’s own thoughts and feelings. Words, objects, or situations that are reminders of the event can also trigger re-experiencing.\n" +
                                    "\n" +
                                    "2. Avoidance symptoms\n" +
                                    "\n" +
                                    "Staying away from places, events, or objects that are reminders of the experience\n" +
                                    "Feeling emotionally numb\n" +
                                    "Feeling strong guilt, depression, or worry\n" +
                                    "Losing interest in activities that were enjoyable in the past\n" +
                                    "Having trouble remembering the dangerous event.\n" +
                                    "Things that remind a person of the traumatic event can trigger avoidance symptoms. These symptoms may cause a person to change his or her personal routine. For example, after a bad car accident, a person who usually drives may avoid driving or riding in a car.\n" +
                                    "\n" +
                                    "3. Hyperarousal symptoms\n" +
                                    "\n" +
                                    "Being easily startled\n" +
                                    "Feeling tense or “on edge”\n" +
                                    "Having difficulty sleeping, and/or having angry outbursts.\n" +
                                    "Hyperarousal symptoms are usually constant, instead of being triggered by things that remind one of the traumatic event. They can make the person feel stressed and angry. These symptoms may make it hard to do daily tasks, such as sleeping, eating, or concentrating.\n" +
                                    "\n" +
                                    "It’s natural to have some of these symptoms after a dangerous event. Sometimes people have very serious symptoms that go away after a few weeks. This is called acute stress disorder, or ASD. When the symptoms last more than a few weeks and become an ongoing problem, they might be PTSD. Some people with PTSD don’t show any symptoms for weeks or months.\n" +
                                    "\n" +
                                    "Do children react differently than adults?\n" +
                                    "\n" +
                                    "Children and teens can have extreme reactions to trauma, but their symptoms may not be the same as adults. In very young children, these symptoms can include:\n" +
                                    "\n" +
                                    "Bedwetting, when they’d learned how to use the toilet before\n" +
                                    "Forgetting how or being unable to talk\n" +
                                    "Acting out the scary event during playtime\n" +
                                    "Being unusually clingy with a parent or other adult.\n" +
                                    "Older children and teens usually show symptoms more like those seen in adults. They may also develop disruptive, disrespectful, or destructive behaviors. Older children and teens may feel guilty for not preventing injury or deaths. They may also have thoughts of revenge. For more information, see the NIMH booklets on helping children cope with violence and disasters. (from Post-Traumatic Stress Disorder (PTSD) )");
                            break;
                        case 4:

                            content.setText("");
                            content.setText("PTSD can occur at any age, including childhood. Women are more likely to develop PTSD than men, and there is some evidence that susceptibility to the disorder may run in families.\n" +
                                    "\n" +
                                    "Anyone can get PTSD at any age. This includes war veterans and survivors of physical and sexual assault, abuse, accidents, disasters, and many other serious events.\n" +
                                    "\n" +
                                    "Not everyone with PTSD has been through a dangerous event. Some people get PTSD after a friend or family member experiences danger or is harmed. The sudden, unexpected death of a loved one can also cause PTSD.\n" +
                                    "\n" +
                                    "Why do some people get PTSD and other people do not?\n" +
                                    "\n" +
                                    "It is important to remember that not everyone who lives through a dangerous event gets PTSD. In fact, most will not get the disorder.\n" +
                                    "\n" +
                                    "Many factors play a part in whether a person will get PTSD. Some of these are risk factors that make a person more likely to get PTSD. Other factors, called resilience factors, can help reduce the risk of the disorder. Some of these risk and resilience factors are present before the trauma and others become important during and after a traumatic event.\n" +
                                    "\n" +
                                    "Risk factors for PTSD include:\n" +
                                    "\n" +
                                    "Living through dangerous events and traumas\n" +
                                    "Having a history of mental illness\n" +
                                    "Getting hurt\n" +
                                    "Seeing people hurt or killed\n" +
                                    "Feeling horror, helplessness, or extreme fear\n" +
                                    "Having little or no social support after the event\n" +
                                    "Dealing with extra stress after the event, such as loss of a loved one, pain and injury, or loss of a job or home.\n" +
                                    "Resilience factors that may reduce the risk of PTSD include:\n" +
                                    "\n" +
                                    "Seeking out support from other people, such as friends and family\n" +
                                    "Finding a support group after a traumatic event\n" +
                                    "Feeling good about one’s own actions in the face of danger\n" +
                                    "Having a coping strategy, or a way of getting through the bad event and learning from it\n" +
                                    "Being able to act and respond effectively despite feeling fear.\n" +
                                    "Researchers are studying the importance of various risk and resilience factors. With more study, it may be possible someday to predict who is likely to get PTSD and prevent it.");

                            break;
                        case 5:
                            content.setText("");
                            content.setText("Not every traumatized person develops full-blown or even minor PTSD. Symptoms usually begin within 3 months of the incident but occasionally emerge years afterward. They must last more than a month to be considered PTSD. The course of the illness varies. Some people recover within 6 months, while others have symptoms that last much longer. In some people, the condition becomes chronic.\n" +
                                    "\n" +
                                    "A doctor who has experience helping people with mental illnesses, such as a psychiatrist or psychologist, can diagnose PTSD. The diagnosis is made after the doctor talks with the person who has symptoms of PTSD.\n" +
                                    "\n" +
                                    "To be diagnosed with PTSD, a person must have all of the following for at least 1 month:\n" +
                                    "\n" +
                                    "- At least one re-experiencing symptom\n" +
                                    "- At least three avoidance symptoms\n" +
                                    "- At least two hyperarousal symptoms\n" +
                                    "\nSymptoms that make it hard to go about daily life, go to school or work, be with friends, and take care of important tasks.\n" +
                                    "\n" +
                                    "PTSD is often accompanied by depression, substance abuse, or one or more of the other anxiety disorders.");
                            break;
                        case 6:
                            content.setText("");
                            content.setText("The main treatments for people with PTSD are psychotherapy (“talk” therapy), medications, or both. Everyone is different, so a treatment that works for one person may not work for another. It is important for anyone with PTSD to be treated by a mental health care provider who is experienced with PTSD. Some people with PTSD need to try different treatments to find what works for their symptoms.\n" +
                                    "\n" +
                                    "If someone with PTSD is going through an ongoing trauma, such as being in an abusive relationship, both of the problems need to be treated. Other ongoing problems can include panic disorder, depression, substance abuse, and feeling suicidal.\n" +
                                    "\n" +
                                    "Psychotherapy\n" +
                                    "\n" +
                                    "Psychotherapy is “talk” therapy. It involves talking with a mental health professional to treat a mental illness. Psychotherapy can occur one-on-one or in a group. Talk therapy treatment for PTSD usually lasts 6 to 12 weeks, but can take more time. Research shows that support from family and friends can be an important part of therapy.\n" +
                                    "\n" +
                                    "Many types of psychotherapy can help people with PTSD. Some types target the symptoms of PTSD directly. Other therapies focus on social, family, or job-related problems. The doctor or therapist may combine different therapies depending on each person’s needs.\n" +
                                    "\n" +
                                    "One helpful therapy is called cognitive behavioral therapy, or CBT. There are several parts to CBT, including:\n" +
                                    "\n" +
                                    "Exposure therapy. This therapy helps people face and control their fear. It exposes them to the trauma they experienced in a safe way. It uses mental imagery, writing, or visits to the place where the event happened. The therapist uses these tools to help people with PTSD cope with their feelings.\n" +
                                    "Cognitive restructuring. This therapy helps people make sense of the bad memories. Sometimes people remember the event differently than how it happened. They may feel guilt or shame about what is not their fault. The therapist helps people with PTSD look at what happened in a realistic way.\n" +
                                    "Stress inoculation training. This therapy tries to reduce PTSD symptoms by teaching a person how to reduce anxiety. Like cognitive restructuring, this treatment helps people look at their memories in a healthy way.\n" +
                                    "Other types of treatment can also help people with PTSD. People with PTSD should talk about all treatment options with their therapist.\n" +
                                    "\n" +
                                    "How Talk Therapies Help People Overcome PTSD\n" +
                                    "\n" +
                                    "Talk therapies teach people helpful ways to react to frightening events that trigger their PTSD symptoms. Based on this general goal, different types of therapy may:\n" +
                                    "\n" +
                                    "Teach about trauma and its effects.\n" +
                                    "Use relaxation and anger control skills.\n" +
                                    "Provide tips for better sleep, diet, and exercise habits.\n" +
                                    "Help people identify and deal with guilt, shame, and other feelings about the event.\n" +
                                    "Focus on changing how people react to their PTSD symptoms. For example, therapy helps people visit places and people that are reminders of the trauma.\n" +
                                    "Medications\n" +
                                    "\n" +
                                    "The U.S. Food and Drug Administration (FDA) has approved two medications for treating adults with PTSD:\n" +
                                    "\n" +
                                    "sertraline (Zoloft)\n" +
                                    "paroxetine (Paxil)\n" +
                                    "Both of these medications are antidepressants, which are also used to treat depression. They may help control PTSD symptoms such as sadness, worry, anger, and feeling numb inside. Taking these medications may make it easier to go through psychotherapy.\n" +
                                    "\n" +
                                    "Sometimes people taking these medications have side effects. The effects can be annoying, but they usually go away. However, medications affect everyone differently. Any side effects or unusual reactions should be reported to a doctor immediately.\n" +
                                    "\n" +
                                    "The most common side effects of antidepressants like sertraline and paroxetine are:\n" +
                                    "\n" +
                                    "Headache, which usually goes away within a few days.\n" +
                                    "Nausea (feeling sick to your stomach), which usually goes away within a few days.\n" +
                                    "Sleeplessness or drowsiness, which may occur during the first few weeks but then goes away.\n" +
                                    "Agitation (feeling jittery).\n" +
                                    "Sexual problems, which can affect both men and women, including reduced sex drive, and problems having and enjoying sex.\n" +
                                    "Sometimes the medication dose needs to be reduced or the time of day it is taken needs to be adjusted to help lessen these side effects.\n" +
                                    "\n" +
                                    "FDA Warning on Antidepressants\n" +
                                    "\n" +
                                    "Despite the relative safety and popularity of SSRIs and other antidepressants, some studies have suggested that they may have unintentional effects on some people, especially adolescents and young adults. In 2004, the U.S. Food and Drug Administration (FDA) conducted a thorough review of published and unpublished controlled clinical trials of antidepressants that involved nearly 4,400 children and adolescents. The review revealed that 4 percent of those taking antidepressants thought about or attempted suicide (although no suicides occurred), compared to 2 percent of those receiving placebos.\n" +
                                    "\n" +
                                    "This information prompted the FDA, in 2005, to adopt a “black box” warning label on all antidepressant medications to alert the public about the potential increased risk of suicidal thinking or attempts in children and adolescents taking antidepressants. In 2007, the FDA proposed that makers of all antidepressant medications extend the warning to include young adults up through age 24. A “black box” warning is the most serious type of warning on prescription drug labeling.\n" +
                                    "\n" +
                                    "The warning emphasizes that patients of all ages taking antidepressants should be closely monitored, especially during the initial weeks of treatment. Possible side effects to look for are worsening depression, suicidal thinking or behavior, or any unusual changes in behavior such as sleeplessness, agitation, or withdrawal from normal social situations. The warning adds that families and caregivers should also be told of the need for close monitoring and report any changes to the physician. The latest information can be found on the FDA website.\n" +
                                    "\n" +
                                    "Results of a comprehensive review of pediatric trials conducted between 1988 and 2006 suggested that the benefits of antidepressant medications likely outweigh their risks to children and adolescents with major depression and anxiety disorders. The study was funded in part by the National Institute of Mental Health.\n" +
                                    "\n" +
                                    "Other Medications\n" +
                                    "\n" +
                                    "Doctors may also prescribe other types of medications, such as the ones listed below. There is little information on how well these work for people with PTSD.\n" +
                                    "\n" +
                                    "Benzodiazepines. These medications may be given to help people relax and sleep. People who take benzodiazepines may have memory problems or become dependent on the medication.\n" +
                                    "Antipsychotics. These medications are usually given to people with other mental disorders, like schizophrenia. People who take antipsychotics may gain weight and have a higher chance of getting heart disease and diabetes.\n" +
                                    "Other antidepressants. Like sertraline and paroxetine, the antidepressants fluoxetine (Prozac) and citalopram (Celexa) can help people with PTSD feel less tense or sad. For people with PTSD who also have other anxiety disorders or depression, antidepressants may be useful in reducing symptoms of these co-occurring illnesses.\n" +
                                    "Treatment After Mass Trauma\n" +
                                    "\n" +
                                    "Sometimes large numbers of people are affected by the same event. For example, a lot of people needed help after Hurricane Katrina in 2005 and the terrorist attacks of September 11, 2001. Most people will have some PTSD symptoms in the first few weeks after events like these. This is a normal and expected response to serious trauma, and for most people, symptoms generally lessen with time. Most people can be helped with basic support, such as:\n" +
                                    "\n" +
                                    "Getting to a safe place\n" +
                                    "Seeing a doctor if injured\n" +
                                    "Getting food and water\n" +
                                    "Contacting loved ones or friends\n" +
                                    "Learning what is being done to help.\n" +
                                    "But some people do not get better on their own. A study of Hurricane Katrina survivors found that, over time, more people were having problems with PTSD, depression, and related mental disorders. This pattern is unlike the recovery from other natural disasters, where the number of people who have mental health problems gradually lessens. As communities try to rebuild after a mass trauma, people may experience ongoing stress from loss of jobs and schools, and trouble paying bills, finding housing, and getting health care. This delay in community recovery may in turn delay recovery from PTSD.\n" +
                                    "\n" +
                                    "In the first couple weeks after a mass trauma, brief versions of CBT may be helpful to some people who are having severe distress. Sometimes other treatments are used, but their effectiveness is not known. For example, there is growing interest in an approach called psychological first aid. The goal of this approach is to make people feel safe and secure, connect people to health care and other resources, and reduce stress reactions. There are guides for carrying out the treatment, but experts do not know yet if it helps prevent or treat PTSD.\n" +
                                    "\n" +
                                    "In single-session psychological debriefing, another type of mass trauma treatment, survivors talk about the event and express their feelings one-on-one or in a group. Studies show that it is not likely to reduce distress or the risk for PTSD, and may actually increase distress and risk.\n" +
                                    "\n" +
                                    "Mass Trauma Affects Hospitals and Other Providers\n" +
                                    "\n" +
                                    "Hospitals, health care systems, and health care providers are also affected by a mass trauma. The number of people who need immediate physical and psychological help may be too much for health systems to handle. Some patients may not find help when they need it because hospitals do not have enough staff or supplies. In some cases, health care providers themselves may be struggling to recover as well.\n" +
                                    "\n" +
                                    "NIMH scientists are working on this problem. For example, researchers are testing how to give CBT and other treatments using the phone and the Internet. In one study, people with PTSD met with a therapist to learn about the disorder, made a list of things that trigger their symptoms, and learned basic ways to reduce stress. After this meeting, the participants could visit a website with more information about PTSD. Participants could keep a log of their symptoms and practice coping skills. Overall, the researchers found the Internet-based treatment helped reduce symptoms of PTSD and depression. These effects lasted after treatment ended.\n" +
                                    "\n" +
                                    "Researchers will carry out more studies to find out if other such approaches to therapy can be helpful after mass trauma.");
                            break;
                        case 7:
                            content.setText("");
                            content.setText("Living with PTSD is by no means easy. Below are some stories of individuals telling their tale. \n" +Html.fromHtml(
                                    "<a href=\"http://www.google.com\">Much more information can be found here.</a>")+
                                    "\n" +
                                    "\n" +
                                    "" +
                                    "“I was raped when I was 25 years old. For a long time, I spoke about the rape as though it was something that happened to someone else. I was very aware that it had happened to me, but there was just no feeling.”\n" +
                                    "\n" +
                                    "“Then I started having flashbacks. They kind of came over me like a splash of water. I would be terrified. Suddenly I was reliving the rape. Every instant was startling. I wasn’t aware of anything around me, I was in a bubble, just kind of floating. And it was scary. Having a flashback can wring you out.”\n" +
                                    "\n" +
                                    "“The rape happened the week before Thanksgiving, and I can’t believe the anxiety and fear I feel every year around the anniversary date. It’s as though I’ve seen a werewolf. I can’t relax, can’t sleep, don’t want to be with anyone. I wonder whether I’ll ever be free of this terrible problem.”");
                            break;
                        case 8:
                            content.setText("");
                            content.setText("The National Institute of Mental Health (NIMH) supports research studies on mental health and disorders. See also: A Participant's Guide to Mental Health Clinical Research.\n" +
                                    "\n" +
                                    "Participate, refer a patient or learn about results of studies in ClinicalTrials.gov , the NIH/National Library of Medicine's registry of federally and privately funded clinical trials for all disease.\n" +
                                    "\n" +
                                    "Studies currently recruiting participants with PTSD can be found ");
                            content.append(Html.fromHtml(
                                    "<a href=\"http://www.google.com\">here</a> " +
                                            "."));
                            content.setMovementMethod(LinkMovementMethod.getInstance());
                            break;
                        default:
                            content.setText("Welcome to the PTSD Information app." +
                                    "\n\nTap on the drowdown menu above to get started on a topic, " +
                                    "or swipe right for more tools such asa map for nearby " +
                                    "clinics, a shortcut to 911, as well as a bit about why this " +
                                    "app was created.\n\n\nYours truly,\n\nThe Penguinologist");
                            break;
                    }


                }


                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }
    }
}


//96+6+315+21+38+364+93+208+276+445=1862 lines of code total