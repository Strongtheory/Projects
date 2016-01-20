#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QObject>

class MainWindow : public QObject
{
    Q_OBJECT
public:
    explicit MainWindow(QObject *rootObject = 0, QObject *parent = 0);

    void setRootObject(QObject *rootObject);
    void showHomeScreen();

private:
    QObject *rootObject;

};

#endif // MAINWINDOW_H
