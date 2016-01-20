#include <QApplication>
#include "maincontroller.h"

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);

    MainController mainController;

    return app.exec();
}
