#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

int main()
{
    cout << "Hello world" << endl;
    ofstream file;
    file.open("example.txt");
    file << "Saving data... \n";
    file << "Line 2" << endl;
    file << "Line 3" << endl;
    file.close();

    string line;
    ifstream myfile("input.txt");
    if(myfile.is_open())
    {
        while(getline(myfile,line))
        {
            cout << line << endl;
        }
        myfile.close();
    }
    else
    {
        cout << "Unable to open file";
    }

    return 0;
}
