#ifndef SETTINGS_H
#define SETTINGS_H

#include <QObject>

class Settings : public QObject
{
    Q_OBJECT
public:
    explicit Settings(QObject *rootObject = 0, QObject *parent = 0);

    void setRootObject(QObject* rootObject);
    void showSettingsPage();

private:
    QObject* rootObject;

};

#endif // SETTINGS_H
