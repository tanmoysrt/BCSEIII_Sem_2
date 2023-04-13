#include<bits/stdc++.h>
using namespace std;
vector<string> productions(string s)
{
    vector<string> prods;
    string a;
    int i=0,n=s.length();
    while(s[i]!='>')
    i++;
    i++;
    for(;i<=n;i++)
    {
        if(i==n||s[i]=='|')
        {
            int f=0,l=a.size()-1;
            while(a[f]==' ')
            f++;
            while(a[l]==' ')
            l--;
            prods.push_back(a.substr(f,l-f+1));
            a="";
        }
        else
        {
            a.push_back(s[i]);
        }
    }
    return prods;
}
int main()
{
    int no_of_prod,i;
    cout<<"Enter number of productions: ";
    cin>>no_of_prod;
    vector<string> prod(no_of_prod);
    map<char,vector<string>> mp;
    cin.ignore();
    for(i=0;i<no_of_prod;i++)
    {
        getline(cin,prod[i]);
        vector<string> prods=productions(prod[i]);
        for(string s: prods)
        mp[prod[i][0]].push_back(s);
    }
    cout<<"\nGrammar without direct left recursion: "<<endl;
    for(auto it: mp)
    {
        vector<string> no_left,left;
        for(auto s: it.second)
        {
            if(s[0]==it.first)
            left.push_back(s.substr(1,s.size()-1));
            else
            {
                if(s=="e")
                no_left.push_back("");
                else
                no_left.push_back(s);
            }
        }
        if(left.empty())
        {
            cout<<it.first<<" -> ";
            for(i=0;i<no_left.size();i++)
            {
                cout<<no_left[i]<<" ";
                if(i!=no_left.size()-1)
                cout<<"| ";
            }
            cout<<endl;
        }
        else
        {
            cout<<it.first<<" -> ";
            for(i=0;i<no_left.size();i++)
            {
                cout<<no_left[i]<<it.first<<"' ";
                if(i!=no_left.size()-1)
                cout<<"| ";
            }
            cout<<endl;
            cout<<it.first<<"' -> ";
            for(i=0;i<left.size();i++)
            {
                cout<<left[i]<<it.first<<"' | ";
            }
            cout<<'e'<<endl;
        }
    }
}