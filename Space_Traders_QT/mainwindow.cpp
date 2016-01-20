#include "mainwindow.h"

#include <QVariant>

MainWindow::MainWindow(QObject *rootObject, QObject *parent) :
    QObject(parent),
    rootObject(rootObject)
{
}

void MainWindow::setRootObject(QObject *rootObject) {
    this->rootObject = rootObject;
}

void MainWindow::showHomeScreen() {
    Q_ASSERT(rootObject);
    rootObject->findChild<QObject*>("pageLoader")->setProperty("source", QStringLiteral("qrc:///mainwindow.qml"));
}
